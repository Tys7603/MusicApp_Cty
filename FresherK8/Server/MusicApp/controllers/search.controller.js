
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
          groupedResults.songs.push({ song_id: result.id, song_name: result.name, song_image : result.image, song_url : result.url, name_artist : result.artist, song_count : result.song_count });
          break;
        case 'playlist':
          groupedResults.playlists.push({ playlist_id: result.id, playlist_name: result.name, playlist_image : result.image, url : result.url, name_artist : result.artist, song_count : result.song_count });
          break;
        case 'music_video':
          groupedResults.music_videos.push({ music_video_id: result.id, music_video_name: result.name, artist_image : result.image, music_video_time : result.url, artist_name : result.artist, music_video_image : result.song_count });
          break;
        case 'album':
          groupedResults.albums.push({ album_id: result.id, album_name: result.name, album_image : result.image, url : result.url, name_artist : result.artist, song_count : result.song_count });
          break;
      }
    });

    res.json({ status: 200, search : groupedResults})
  } catch (error) {
    res.json({ status: "400", message: error.message });
  }

}

const getListSearchAllNameController = async (req, res) => {

  try {

    const search = await model.searchNameAllModel()

    res.json({ status: 200, search })

  } catch (error) {
    res.json({ status: "400", message: error.message });
  }

}

module.exports = {
  getListSearchController,
  getListSearchAllNameController
}
