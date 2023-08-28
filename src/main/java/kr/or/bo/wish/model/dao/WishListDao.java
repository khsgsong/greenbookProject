package kr.or.bo.wish.model.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.or.bo.wish.model.vo.WishListRowMapper;

@Repository
public class WishListDao {
	@Autowired
	private JdbcTemplate jdbc;
	@Autowired
	private WishListRowMapper wishListRowMapper;
	
	public int insertWish(int productBoardNo, String memberId) {
		String query = "insert into wish_list values(wish_list_seq.nextval, ?, ?)";
		Object[] params = {productBoardNo, memberId};
		int result = jdbc.update(query, params);
		return result;
	}

	public int deleteWish(int productBoardNo, String memberId) {
		String query = "delete from wish_list where product_board_no = ? and member_id = ?";
		Object[] params = {productBoardNo, memberId}; 
		int result = jdbc.update(query, params);
		return result;
	}

	public List selectMyWishList(int start, int end, String memberId) {
		String query = "select * from (select rownum as rnum, w.* from (select wish_list_no, product_board_no, member_id, product_board_writer, product_price, product_sell_check, product_board_title, filepath from wish_list join product_board using (product_board_no) join product_file on (product_board_no = product_no) where member_id = ? order by 1 desc)w) where rnum between ? and ?";
		List list = jdbc.query(query, wishListRowMapper, memberId, start, end);
		return list;
	}

	public int selectMyWishListTotalCount(String memberId) {
		String query = "select count(*) as cnt from wish_list where member_id = ?";
		int totalCount = jdbc.queryForObject(query, Integer.class, memberId);
		return totalCount;
	}
}
