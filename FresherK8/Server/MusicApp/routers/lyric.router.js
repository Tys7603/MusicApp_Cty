
const express = require('express');
const router = express.Router();
const controller = require('../controllers/lyric.controller.js');

router.get('/lyrics/:songId',controller.getLyricBySongIdController);

module.exports = router;
