
const model = require('../models/songModel.js')

const getListSongController = async (req, res) => {

  try {
    const songs = await model.getListSong()

    res.json({ status: 200, songs })
  } catch (error) {
    res.json({ status: "400", error });
  }

}

module.exports = {
  getListSongController
}
