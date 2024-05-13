
const express = require('express');
const router = express.Router();
const controller = require('../controllers/playlistController.js');

router.get('/playlists', controller.getListPlaylistController);
router.get('/playlists/mood/today', controller.getListPlaylistMoodTodayController);
router.post('/playlist/user', controller.createPlaylistUserController);
router.get('/playlists/:userId', controller.getListPlaylistByUserIdController);

module.exports = router;
