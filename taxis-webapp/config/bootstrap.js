/**
 * Bootstrap
 * (sails.config.bootstrap)
 *
 * An asynchronous bootstrap function that runs before your Sails app gets lifted.
 * This gives you an opportunity to set up your data model, run jobs, or perform some special logic.
 *
 * For more information on bootstrapping your app, check out:
 * http://sailsjs.org/#/documentation/reference/sails.config/sails.config.bootstrap.html
 */

module.exports.bootstrap = function(cb) {

  // It's very important to trigger this callback method when you are finished
  // with the bootstrap!  (otherwise your server will never lift, since it's waiting on the bootstrap)
  
  //cb();

  Driver.count(function (err, num) {
    if(err) {
      return console.log(err);
    }
    console.log(num);
    if (num > 0) { 
    } else {
      var fs = require('fs')
      fs.readFile('db/seed/driver.csv', 'utf8', function (err, data) {
        if (err) {
          return console.log(err);
        }
        var lines = data.split('\n')
        var arrayLength = lines.length;
        for (var i = 0; i < arrayLength; i++) {
          var driver = lines[i].split(',');
          Driver.create({
              name: driver[0],
              lastname: driver[1],
              surname: driver[2],
              plates: driver[3],
              brand: driver[4],
              model: driver[5],
              year: driver[6]
            }).exec(function(e,driver){
            if(e) console.log(e);
          }); 
        }
      });
    }
    return cb();
  });
};