
const express = require('express');
const router = express.Router();
const controller = require('../controllers/albumController.js');

router.get('/albums/new',controller.getListAlbumNewController);
router.get('/albums/love',controller.getListAlbumLoveController);

module.exports = router;
