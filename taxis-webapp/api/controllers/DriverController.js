/**
 * DriverController
 *
 * @description :: Server-side logic for managing drivers
 * @help        :: See http://links.sailsjs.org/docs/controllers
 */

module.exports = {
  index: function (req, res) {
    Driver.find({ limit: 80, sort: 'name DESC' }).exec(function(e,drivers){
      res.view({
        drivers: drivers
      });
    });
  },
  show: function (req, res) {
    Driver.find({ id: req.params.id }).exec(function(e,driver){
      res.view({
        driver: driver
      });
    });
  }
};

