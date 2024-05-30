
const express = require('express');
const router = express.Router();
const controller = require('../controllers/search.controller.js');

router.get('/search/:keyword', controller.getListSearchController);
router.get('/search/all/name', controller.getListSearchAllNameController);

module.exports = router;
