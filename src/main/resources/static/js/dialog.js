//dialog.js
const DELETE_MESSAGE = "削除してもよろしいですか？"
$('.delete-action').click(function() {
	if(!confirm(DELETE_MESSAGE)){
		return false;
	}
});

const CHANGE_MESSAGE = "変更してもよろしいですか？"
$('.change-action').click(function() {
	if(!confirm(CHANGE_MESSAGE)){
		return false;
	}
});