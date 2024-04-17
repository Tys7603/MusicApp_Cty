
const express = require('express');
const router = express.Router();
const controller = require('../controllers/albumLoveController.js');

router.get('/albums/love',controller.getListAlbumLoveController);

module.exports = router;
