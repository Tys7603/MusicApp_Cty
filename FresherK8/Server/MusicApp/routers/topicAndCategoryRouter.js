
const express = require('express');
const router = express.Router();
const controller = require('../controllers/topicAndCategoryController.js');

router.get('/topics_categories',controller.getListTopicAndCategoryController);

module.exports = router;
