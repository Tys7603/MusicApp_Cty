
const model = require('../models/playlistModel.js')

const getListPlaylistController = async (req, res) => {

  try {
    const playlists = await model.getListPlaylist()
    res.json({ status: 200, playlists })
  } catch (error) {
    res.json({ status: "400", error });
  }

}

const getListPlaylistMoodTodayController = async (req, res) => {

  try {
    const playlists = await model.getListPlaylistMoodToday()
    res.json({ status: 200, playlists })
  } catch (error) {
    res.json({ status: "400", error });
  }

}


module.exports = {
  getListPlaylistController,
  getListPlaylistMoodTodayController
}
