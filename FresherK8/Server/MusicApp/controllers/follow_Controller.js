const model = require('../models/follow_model.js')

// Theo dõi nghệ sĩ
const insertFollowTheArtistController = async (req, res) => {
  try {
    const {userId, artistId} = req.body

    const follows = await model.insertFollowTheArtist(userId, artistId)

    res.json({ status: 200 , follows  })

  } catch (error) {
    res.json({ status: "400", message : error.message });
  }
}

// Kiểm tra xem người dùng và nghệ sĩ đã được theo dõi chưa
const checkFollowTheArtistController = async (req, res) => {
  try {
    const {userId, artistId} = req.params

    const follows = await model.checkFollowTheArtist(userId, artistId)

    res.json({ status: 200 , follows })

  } catch (error) {
    res.json({ status: "400", message : error.message });
  }
}

// Lấy số lượng nghệ sĩ đã theo dõi theo id người dùng
const quantityFollowTheArtistByUserController = async (req, res) => {
  try {
    const {userId} = req.params

    const follows = await model.quantityFollowTheArtistByUser(userId)

    res.json({ status: 200 , follows })

  } catch (error) {
    res.json({ status: "400", message : error.message });
  }
}

// Lấy danh sách nghệ sĩ đã theo dõi theo id người dùng
const getFollowTheArtistByUserController = async (req, res) => {
  try {
    const {userId} = req.params

    const follows = await model.getFollowTheArtistByUser(userId)

    res.json({ status: 200 , follows  })

  } catch (error) {
    res.json({ status: "400", message : error.message });
  }
}

// Bỏ theo dõi nghệ sĩ
const deleteFollowTheArtistByUserController = async (req, res) => {

  try {
    const {userId, artistId} = req.params

    const follows = await model.deleteFollowTheArtistByUser(userId, artistId)

    res.json({ status: 200 , follows })

  } catch (error) {
    res.json({ status: "400", message : error.message });
  }
}

module.exports = {
  insertFollowTheArtistController,
  checkFollowTheArtistController,
  quantityFollowTheArtistByUserController,
  getFollowTheArtistByUserController,
  deleteFollowTheArtistByUserController
}
