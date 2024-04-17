
const model = require('../models/albumLoveModel.js')

const getListAlbumLoveController = async (req, res) => {

  try {
    const albumLoves = await model.getListAlbumLove()

    res.json({ status: 200 , albumLoves  })
  } catch (error) {
    res.json({ status: "400", error });
  }

}

module.exports = {
    getListAlbumLoveController
}
