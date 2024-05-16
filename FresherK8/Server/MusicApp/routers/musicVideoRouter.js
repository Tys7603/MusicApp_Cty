
const express = require('express');
const router = express.Router();
const controller = require('../controllers/musicVideoControllers.js');

router.get('/musics/video', controller.getListMusicVideoController);
router.get('/musics/video/:musicVideoId', controller.getListMusicVideoExcludingIdController);

module.exports = router;
