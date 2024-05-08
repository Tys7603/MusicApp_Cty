
const model = require('../models/playlistModel.js')

const getListPlaylistController = async (req, res) => {

  try {
    const playlists = await model.getListPlaylist()

    
    // Tạo một đối tượng Map để lưu trữ các playlist theo tên
    const playlistMap = new Map();

    // Duyệt qua danh sách playlists và gộp chung name_artist cho các playlist cùng tên
    playlists.forEach(playlist => {
      const { playlist_id, playlist_name, playlist_image, name_artist } = playlist;
      if (playlistMap.has(playlist_name)) {
        // Nếu playlist_name đã tồn tại trong Map, cập nhật name_artist
        const existingPlaylist = playlistMap.get(playlist_name);

        existingPlaylist.name_artist += `, ${name_artist}`;
      } else {
        // Nếu playlist_name chưa tồn tại trong Map, thêm mới

        playlistMap.set(playlist_name, {playlist_id, playlist_name, playlist_image, name_artist });
      }
    });

    // Chuyển đổi Map thành mảng và gửi về client
    const mergedPlaylists = Array.from(playlistMap.values());
    
    res.json({ status: 200, playlists: mergedPlaylists });

  } catch (error) {
    res.json({ status: "400",  message: error.message });
  }

}

const getListPlaylistMoodTodayController = async (req, res) => {

  try {
    const playlists = await model.getListPlaylistMoodToday()
    res.json({ status: 200, playlists })
  } catch (error) {
    res.json({ status: "400",  message: error.message });
  }

}

// add playlist love

const createPlaylistLoveController = async (req, res) => {

  try {
    const {namePlaylist , userId} = req.body

    const playlists = await model.createPlaylistLove(namePlaylist, userId)

    res.json({ status: 200, playlists })

  } catch (error) {
    res.json({ status: "400",  message: error.message });
  }

}

module.exports = {
  getListPlaylistController,
  getListPlaylistMoodTodayController,
  createPlaylistLoveController
}
