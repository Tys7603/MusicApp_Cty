
const model = require('../models/songModel.js')

const getListSongController = async (req, res) => {

  try {
    const songs = await model.getListSong()

    res.json({ status: 200, songs })
  } catch (error) {
    res.json({ status: "400", error });
  }

}

const getListSongByPlaylistIdController = async (req, res) => {

  try {
    const { playlistId } = req.params
    const songs = await model.getListSongByPlaylistId(playlistId)
    res.json({ status: 200, songs })
  } catch (error) {
    res.json({ status: "400", error });
  }

}


const getListSongByTopicIdController = async (req, res) => {

  try {
    const { topicId } = req.params
    const songs = await model.getListSongByTopicId(topicId)
    res.json({ status: 200, songs })
  } catch (error) {
    res.json({ status: "400", error });
  }

}


module.exports = {
  getListSongController,
  getListSongByPlaylistIdController,
  getListSongByTopicIdController
}
