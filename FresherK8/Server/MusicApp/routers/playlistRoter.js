
const express = require('express');
const router = express.Router();
const controller = require('../controllers/playlistController.js');

router.get('/playlists', controller.getListPlaylistController);
router.get('/playlists/mood/today', controller.getListPlaylistMoodTodayController);
router.get('/playlists/:userId', controller.getListPlaylistByUserIdController);
router.post('/playlist/user', controller.createPlaylistUserController);
router.post('/playlist/user/song', controller.createSongIntoPlaylistByUserIdController);
router.delete('/playlistsUser', controller.deletePlaylistUserByIdController);

module.exports = router;
