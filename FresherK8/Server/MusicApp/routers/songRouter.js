
const express = require('express');
const router = express.Router();
const controller = require('../controllers/songController.js');

router.get('/songs', controller.getListSongController);
router.get('/songs/playlist/:playlistId', controller.getListSongByPlaylistIdController);
router.get('/songs/topic/:topicId', controller.getListSongByTopicIdController);

module.exports = router;
