
const express = require('express');
const router = express.Router();
const controller = require('../controllers/topicController.js');

router.get('/topics',controller.getListTopicController);

module.exports = router;
