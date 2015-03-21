/**
 * IndexController
 *
 * @description :: Server-side logic for managing drivers
 * @help        :: See http://links.sailsjs.org/docs/controllers
 */

module.exports = {
  index: function (req, res) {
    //if(req.isJson) {
    //  res.json(user); 
    //}
    //else {
    //  res.redirect('/user');  
    //}

    Driver.count().exec(function(e,num){
      res.view({
        drivers: num
      });
    });
  }
};

