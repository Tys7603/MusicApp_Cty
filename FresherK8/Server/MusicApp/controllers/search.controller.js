
const model = require('../models/search.model.js')

const getListSearchController = async (req, res) => {

  try {
    const groupedResults = {
      songs: [],
      playlists: [],
      music_videos: [],
      albums: []
    };

    const { keyword } = req.params
    const search = await model.searchModel(keyword)

    search.forEach(result => {
      switch (result.type) {
        case 'song':
          groupedResults.songs.push({ id: result.id, name: result.name });
          break;
        case 'playlist':
          groupedResults.playlists.push({ id: result.id, name: result.name });
          break;
        case 'music_video':
          groupedResults.music_videos.push({ id: result.id, name: result.name });
          break;
        case 'album':
          groupedResults.albums.push({ id: result.id, name: result.name });
          break;
      }
    });

    res.json({ status: 200, search: groupedResults })
  } catch (error) {
    res.json({ status: "400", message: error.message });
  }

}

const getListSearchAllNameController = async (req, res) => {

  try {

    const searchAllName = await model.searchNameAllModel()

    res.json({ status: 200, searchAllName })

  } catch (error) {
    res.json({ status: "400", message: error.message });
  }

}

module.exports = {
  getListSearchController,
  getListSearchAllNameController
}
