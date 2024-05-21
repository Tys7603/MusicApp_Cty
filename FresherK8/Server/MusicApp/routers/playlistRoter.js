
const express = require('express');
const router = express.Router();
const controller = require('../controllers/playlistController.js');

router.get('/playlists', controller.getListPlaylistController);
router.get('/playlists/mood/today', controller.getListPlaylistMoodTodayController);
router.get('/playlists/:userId', controller.getListPlaylistByUserIdController);
router.get('/playlists/love/:userId', controller.getListPlaylistLoveByUserIdController);
router.post('/playlist/user', controller.createPlaylistUserController);
router.post('/playlist/user/song', controller.createSongIntoPlaylistByUserIdController);
router.post('/playlist/user/love', controller.inserPlaylistIntoPlaylistLoveByUserIdController);
router.delete('/playlistsUser', controller.deletePlaylistUserByIdController);
router.delete('/playlistsLove', controller.deletePlaylistLoveByIdController);

module.exports = router;
