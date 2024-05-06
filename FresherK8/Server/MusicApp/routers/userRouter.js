
const express = require('express');
const router = express.Router();
const controller = require('../controllers/userController.js');

router.post('/user',controller.createUserController);

module.exports = router;
