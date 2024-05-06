
const model = require('../models/albumModel.js')

const getListAlbumNewController = async (req, res) => {

  try {
    const albums = await model.getListAlbumNew()

    res.json({ status: 200 , albums  })
  } catch (error) {
    res.json({ status: "400", error });
  }

}

const getListAlbumLoveController = async (req, res) => {

  try {
    const albums = await model.getListAlbumLove()

    res.json({ status: 200 , albums  })
  } catch (error) {
    res.json({ status: "400", error });
  }

}

module.exports = {
  getListAlbumNewController,
  getListAlbumLoveController
}
