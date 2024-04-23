
const express = require('express');
const router = express.Router();
const controller = require('../controllers/topicController.js');

router.get('/topics',controller.getListTopicController);
router.get('/topics/categories/:categoryId',controller.getListCategoryByIdTopicController);

module.exports = router;
