
const express = require('express');
const router = express.Router();
const controller = require('../controllers/albumController.js');

router.get('/albums/new',controller.getListAlbumNewController);

module.exports = router;
