
const model = require('../models/songRankModel.js')

const getListSongRankController = async (req, res) => {

  try {
    const results = await model.getListSongRank();

    // Tạo mảng JSON từ kết quả truy vấn
    const songRanks = results.reduce((acc, curr) => {
      // Kiểm tra xem rank_name đã tồn tại trong mảng chưa
      const existingRank = acc.find(item => item.rank_name === curr.rank_name);

      // Nếu rank_name đã tồn tại, thêm bài hát vào mảng bài hát của rank đó
      if (existingRank) {
        existingRank.songs.push({
          song_name: curr.song_name,
          song_image: curr.song_image,
          song_url: curr.song_url,
          name_artist: curr.name_artist
        });
      } else {
        // Nếu rank_name chưa tồn tại, tạo một đối tượng mới và thêm vào mảng
        acc.push({
          rank_name: curr.rank_name,
          songs: [{
            song_name: curr.song_name,
            song_image: curr.song_image,
            song_url: curr.song_url,
            name_artist: curr.name_artist
          }]
        });
      }

      return acc;
    }, []);

    res.json({ status: 200, songRanks });
  } catch (error) {
    res.json({ status: 400, message: error.message });
  }
};

module.exports = {
  getListSongRankController
}
