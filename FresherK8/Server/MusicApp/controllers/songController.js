
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


const getListSongByAlbumIdController = async (req, res) => {

  try {
    const { albumId } = req.params
    const songs = await model.getListSongByAlbumId(albumId)
    res.json({ status: 200, songs })
  } catch (error) {
    res.json({ status: "400", error });
  }

}

const getListSongLoveByUserIdController = async (req, res) => {

  try {
    const { userId } = req.params
    const songs = await model.getListSonglove(userId)
    res.json({ status: 200, songs })
  } catch (error) {
    res.json({ status: "400", error });
  }

}

const createSongLoveController = async (req, res) => {

  try {
    const { userId, songId } = req.body
    const songs = await model.createSongLove(userId, songId)
    res.json({ status: 200, songs })
  } catch (error) {
    res.json({ status: "400", error });
  }

}

const deleteSongLoveController = async (req, res) => {

  try {
    const { songLoveId } = req.params
    const songs = await model.deleteSongLove(songLoveId)
    res.json({ status: 200, songs })
  } catch (error) {
    res.json({ status: "400", error });
  }

}


module.exports = {
  getListSongController,
  getListSongByPlaylistIdController,
  getListSongByTopicIdController,
  getListSongLoveByUserIdController,
  createSongLoveController,
  deleteSongLoveController,
  getListSongByAlbumIdController
}
