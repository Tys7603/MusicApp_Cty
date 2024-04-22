
const express = require('express');
const router = express.Router();
const controller = require('../controllers/playlistController.js');

router.get('/playlists', controller.getListPlaylistController);
router.get('/playlists/mood/today', controller.getListPlaylistMoodTodayController);

module.exports = router;
