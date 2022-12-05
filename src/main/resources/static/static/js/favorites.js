$(function() {
	/*商品列表，鼠标移入时加阴影、移出移除阴影*/
	$(".goods-panel").hover(function() {
		$(this).css("box-shadow", "0px 0px 8px #888888");
	}, function() {
		$(this).css("box-shadow", "");
	})
})