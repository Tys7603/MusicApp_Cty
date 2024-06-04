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
    const query = ` SELECT *, COUNT(f.artist_id) as follow_count FROM FOLLOW as f
    INNER JOIN ARTIST as a ON f.artist_id  = a.artist_id
    WHERE user_id = ?
    `
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
