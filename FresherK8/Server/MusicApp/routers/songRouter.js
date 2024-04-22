
const express = require('express');
const router = express.Router();
const controller = require('../controllers/songController.js');

router.get('/songs', controller.getListSongController);

module.exports = router;