
const express = require('express');
const router = express.Router();
const controller = require('../controllers/follow_Controller');

// Theo dõi nghệ sĩ
router.post('/follow',controller.insertFollowTheArtistController);
// Kiểm tra xem người dùng và nghệ sĩ đã được theo dõi chưa
router.get('/followed/:userId/:artistId',controller.checkFollowTheArtistController);
// Lấy số lượng nghệ sĩ đã theo dõi theo id người dùng
router.get('/follow/quantity/:userId',controller.quantityFollowTheArtistByUserController);
// Lấy danh sách nghệ sĩ đã theo dõi theo id người dùng
router.get('/follows/:userId',controller.getFollowTheArtistByUserController);
// Bỏ theo dõi nghệ sĩ
router.delete('/follow/:userId/:artistId',controller.deleteFollowTheArtistByUserController);

module.exports = router;
