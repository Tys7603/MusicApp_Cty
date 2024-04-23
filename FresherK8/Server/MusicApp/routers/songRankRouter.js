
const express = require('express');
const router = express.Router();
const controller = require('../controllers/songRankController.js');

router.get('/songs/rank/listen', controller.getListSongRankController);

module.exports = router;
