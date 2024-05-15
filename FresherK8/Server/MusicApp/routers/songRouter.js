
const express = require('express');
const router = express.Router();
const controller = require('../controllers/songController.js');

router.get('/songs', controller.getListSongController);
router.get('/songs/playlist/:playlistId', controller.getListSongByPlaylistIdController);
router.get('/songs/love/:userId', controller.getListSongLoveByUserIdController);
router.get('/songs/topic/:topicId', controller.getListSongByTopicIdController);
router.get('/songs/album/:albumId', controller.getListSongByAlbumIdController);
router.post('/song/love', controller.createSongLoveController);
router.delete('/song/love/:songLoveId', controller.deleteSongLoveController);


module.exports = router;
