
const express = require('express');
const router = express.Router();
const controller = require('../controllers/songAgainController.js');

router.get('/songs/Again/:id',controller.getListSongAgainController);

module.exports = router;
