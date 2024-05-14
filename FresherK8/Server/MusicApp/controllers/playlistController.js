
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

        playlistMap.set(playlist_name, { playlist_id, playlist_name, playlist_image, name_artist });
      }
    });

    // Chuyển đổi Map thành mảng và gửi về client
    const mergedPlaylists = Array.from(playlistMap.values());

    res.json({ status: 200, playlists: mergedPlaylists });

  } catch (error) {
    res.json({ status: "400", message: error.message });
  }

}

const getListPlaylistMoodTodayController = async (req, res) => {

  try {
    const playlists = await model.getListPlaylistMoodToday()
    res.json({ status: 200, playlists })
  } catch (error) {
    res.json({ status: "400", message: error.message });
  }

}

// create playlist user
const createPlaylistUserController = async (req, res) => {

  try {
    const { namePlaylist, userId } = req.body

    const playlists = await model.createPlaylistUser(namePlaylist, userId)

    res.json({ status: 200, playlists })

  } catch (error) {
    res.json({ status: "400", message: error.message });
  }

}

// get playlist love

const getListPlaylistByUserIdController = async (req, res) => {
  try {
    const { userId } = req.params;
    const defaultImageUrl = "https://iili.io/HlHy9Yx.png";

    const playlists = await model.getListPlaylistByIdUser(userId);

    const updatedPlaylists = playlists.map(playlist => {
      if (!playlist.song_image) {
        playlist.song_image = defaultImageUrl;
      }
      return playlist;
    });

    res.json({ status: 200, playlists: updatedPlaylists });
  } catch (error) {
    res.json({ status: 400, message: error.message });
  }
}

const deletePlaylistUserByIdController = async (req, res) => {
  try {
    const { playlistUserId } = req.query;

    const listPlaylistUser = JSON.parse(playlistUserId)

    const playlists = await model.deletePlaylistUserById(listPlaylistUser);

    res.json({ status: 200, playlists });
  } catch (error) {
    res.json({ status: 400, message: error.message });
  }
}

const createSongIntoPlaylistByUserIdController = async (req, res) => {
  try {
    const { playlistUserId, songId } = req.body;

    const playlists = await model.createSongIntoPlaylistByUserId(playlistUserId, songId);

    res.json(playlists);
  } catch (error) {
    res.json({ status: 400, message: error.message });
  }
}

module.exports = {
  getListPlaylistController,
  getListPlaylistMoodTodayController,
  getListPlaylistByUserIdController,
  createPlaylistUserController,
  deletePlaylistUserByIdController,
  createSongIntoPlaylistByUserIdController
}
