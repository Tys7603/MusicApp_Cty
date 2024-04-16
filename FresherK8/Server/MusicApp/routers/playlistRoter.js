
const express = require('express');
const router = express.Router();
const controller = require('../controllers/playlistController.js');

router.get('/playlists',controller.getListPlaylistController);

module.exports = router;
