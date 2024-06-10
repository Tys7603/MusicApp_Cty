const queryDatabase = require("../database/database.js")

// Theo dõi nghệ sĩ
const insertFollowTheArtist= async (userId, artistId) => {
  const query = ` INSERT INTO FOLLOW (user_id, artist_id) VALUE(?,?) `
  return await queryDatabase(query, [userId, artistId])
}

// Kiểm tra xem người dùng và nghệ sĩ đã được theo dõi chưa
const checkFollowTheArtist = async (userId, artistId) => {
    const query = ` SELECT COUNT(follow_id) as isFollow FROM FOLLOW 
    WHERE user_id = ? AND artist_id = ?
    `
    return await queryDatabase(query, [userId, artistId])
}

// Lấy số lượng nghệ sĩ đã theo dõi theo id người dùng
const quantityFollowTheArtistByUser = async (userId) => {
    const query = ` SELECT COUNT(follow_id) as quantity FROM FOLLOW 
    WHERE user_id = ?
    `
    return await queryDatabase(query, [userId])
}

// Lấy danh sách nghệ sĩ đã theo dõi theo id người dùng
const getFollowTheArtistByUser = async (userId) => {
    const query =  `SELECT 
    follow1.follow_id,
    artist.artist_id,
    artist.artist_name,
    artist.artist_image,
    COUNT(follow2.follow_id) AS follower_count
    FROM artist
    JOIN follow AS follow1 ON artist.artist_id = follow1.artist_id
    LEFT JOIN follow AS follow2 ON artist.artist_id = follow2.artist_id
    WHERE follow1.user_id = ?
    GROUP BY artist.artist_id`

   return await queryDatabase(query, [userId])
}

// Bỏ theo dõi nghệ sĩ
const deleteFollowTheArtistByUser = async (userId, artistId) => {
    const query = ` DELETE FROM FOLLOW
    WHERE user_id = ? AND artist_id = ?
    `
    return await queryDatabase(query, [userId, artistId])
}
  
module.exports = {
    insertFollowTheArtist,
    checkFollowTheArtist,
    quantityFollowTheArtistByUser,
    getFollowTheArtistByUser,
    deleteFollowTheArtistByUser
}
