
const express = require('express');
const router = express.Router();
const controller = require('../controllers/musicVideoControllers.js');

router.get('/musics/video', controller.getListMusicVideoController);

module.exports = router;
