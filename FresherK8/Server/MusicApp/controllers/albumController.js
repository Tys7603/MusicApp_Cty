
const model = require('../models/albumModel.js')

const getListAlbumNewController = async (req, res) => {

  try {
    const albumNews = await model.getListAlbumNew()

    res.json({ status: 200 , albumNews  })
  } catch (error) {
    res.json({ status: "400", error });
  }

}

module.exports = {
  getListAlbumNewController
}
