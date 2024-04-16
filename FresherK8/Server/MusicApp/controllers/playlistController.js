
const model = require('../models/playlistModel.js')

const getListPlaylistController = async (req, res) => {

  try {
    const playlists = await model.getListPlaylist()
    res.json({ status: 200, playlists })
  } catch (error) {
    res.json({ status: "400", error });
  }

}

module.exports = {
  getListPlaylistController
}
