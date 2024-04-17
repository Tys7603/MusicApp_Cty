
const express = require('express');
const router = express.Router();
const controller = require('../controllers/categoryController.js');

router.get('/categories',controller.getListCategoryController);

module.exports = router;
