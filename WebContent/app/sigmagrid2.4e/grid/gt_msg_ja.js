Sigma.Msg.Grid.ja={
	LOCAL	: "JA",
	ENCODING		: "UTF-8",
	NO_DATA : "", //"No Data",


	GOTOPAGE_BUTTON_TEXT: 'Go To',

	FILTERCLEAR_TEXT: "Remove All Filters",
	SORTASC_TEXT	: "Ascend",
	SORTDESC_TEXT	: "Descend",
	SORTDEFAULT_TEXT: "Original",

	ERR_PAGENUM		: "Page number must be an integer between 1 to #{1}.",

	EXPORT_CONFIRM	: "", //"This operation will affect all records of the whole table.\n\n( Press \"Cancel\" to limit scope to current page.)",
	OVER_MAXEXPORT	: "", //"Number of record exceeds #{1}, the maximum allowed.",

	PAGE_STATE		: "#{1} - #{2} / #{4} レコード,  全 #{3} ページ",
	PAGE_STATE_FULL	: "Page #{5}, #{1} - #{2} displayed,  #{3}page(s) #{4} record(s) totally.",

	SHADOWROW_FAILED: "", //"Relevant info not available",
	NEARPAGE_TITLE	: "",
	WAITING_MSG : 'しばらくお待ち下さい。',

	NO_RECORD_UPDATE: "変更なし",
	UPDATE_CONFIRM	: "Are you sure to save them?",
	NO_MODIFIED: "変更なし",

	
	PAGE_BEFORE : '', //'Page',
	PAGE_AFTER : '',

	PAGESIZE_BEFORE :   '',
	PAGESIZE_AFTER :   '&nbsp;/ ページ',

	RECORD_UNIT : '',
	
	CHECK_ALL : 'Check All',

	COLUMNS_HEADER : 'Columns',

	DIAG_TITLE_FILTER : 'Filter Options',
	DIAG_NO_FILTER : 'No Filter',
	TEXT_ADD_FILTER	: "Add",
	TEXT_CLEAR_FILTER	: "Remove All",
	TEXT_OK	: "OK",
	TEXT_DEL : "削除",
	TEXT_CANCEL	: "キャンセル",
	TEXT_CLOSE	: "閉じる",
	TEXT_UP : "Up",
	TEXT_DOWN : "Down",

	NOT_SAVE : "Do you want to save the changes? \n Click \"Cancel\" to discard.",

	DIAG_TITLE_CHART  : 'Chart',

	CHANGE_SKIN : "Skins",

	STYLE_NAME_DEFAULT : "Classic",
	STYLE_NAME_PINK : "Pink",
	STYLE_NAME_VISTA : "Vista",
	STYLE_NAME_MAC : "Mac",

	MENU_FREEZE_COL : "列の固定",
	MENU_SHOW_COL : "列の非表示",
	MENU_GROUP_COL : "Group Span",

	TOOL_RELOAD : "Refresh" ,
	TOOL_ADD : "追加" ,
	TOOL_DEL : "削除" ,
	TOOL_SAVE : "保存" ,

	TOOL_PRINT : "印刷" ,
	TOOL_XLS : "Export to xls" ,
	TOOL_PDF : "Export to pdf" ,
	TOOL_CSV : "Export to csv" ,
	TOOL_XML : "Export to xml",
	TOOL_FILTER : "Filter" ,
	TOOL_CHART : "Chart" ,
	
	// Start adding custom messages here
	INVALID_NUMBER : 'は数値を入力して下さい。',
	INVALID_INPUT : '入力が正しくありません。',
	REQUIRED_FIELDS_NOT_POPULATED : '必須項目を入力して下さい。',
	//SAVED : '登録されました。',
	SAVED : '登録しました。',
	PUBLISHED : '公開しました。',
	UNPUBLISHED : '非公開にしました。',
	CONFIRM_FINALIZE : '確定しますか？',
	CONFIRM_INVISIBLE: '数量が入力されています。削除してもよろしいですか？',
	ERROR_INVISIBLE : 'すでに発注数量が入力されているため、非表示にできません。',
	FINALIZED : '確定しました。',
	UNFINALIZED : '確定を解除しました。',
	ORDER_CONFIRM_UNFINALIZE : '確定解除しますか？',
	SKU_MAX_LIMIT_EXCEEDED : '発注できる数量は#{1}です。',
	BUYER_SKU_MAX_LIMIT_EXCEEDED : '受注上限数を超えています。「発注可能数量は#{1}」です。',
	CONFIRM_DELETE : '削除してもよろしいですか？',
	UNIT_ORDER_ERROR1 : '変更できません。数量に小数点が含まれています。',
	UNIT_ORDER_ERROR2 : '変更できません。受注上限数に小数点が含まれています。',
	CONFIRM_LOCK : '確定しますか？',
	CONFIRM_UNLOCK : '解除しますか？',
	LOCKED : '確定しました。',
	UNLOCKED : '解除しました。',
	CONFIRM_APPROVE : '承認してもよろしいですか？',
	CONFIRM_UNAPPROVE : '解除してもよろしいですか？',
	APPROVED : '承認しました',
	UNAPPROVED : '解除しました。',
	SKU_DELETE_ERROR : 'すでに発注数量が入力されているため、削除できません。',
	PRICE_WO_TAX : '店着税抜小計', 												
	PRICE_W_TAX : '店着税込小計',
	SELLING_PRICE : '売価小計',
	PROFIT : '粗利額小計',
	PROFIT_PERCENTAGE : '粗利率',
	GT_PRICE_WO_TAX : '店着税抜合計',
	GT_PRICE_W_TAX : '店着税込合計',
	GT_SELLING_PRICE : '売価合計',
	GT_PROFIT : '粗利額合計',
	GT_PROFIT_PERCENTAGE : '粗利率',
	SKU_LIMIT_LESS_THAN_TOTAL_QTY : '発注総数#{1}未満は入力できません。'

};

Sigma.Msg.Grid['ja']=Sigma.Msg.Grid.ja;

Sigma.Msg.Validator.ja={

		'required'	: '商品名は必須です。',
		'date'		: '{0#This field} must be in proper format ({1#YYYY-MM-DD}).',
		'time'		: '{0#This field} must be in proper format ({1#HH:mm}).',
		'datetime'	: '{0#This field} must be in proper format ({1#YYYY-MM-DD HH:mm}).',
		'email'		: '{0#This field} must be in proper email format.',
		'telephone'	: '{0#This field} must be in proper phone no format.',
		'number'	: 'は数値を入力して下さい。',
		'integer'	: 'は数値を入力して下さい。',
		'float'		: '{0} must be integer or decimal.',
		'money'		: '{0} must be integer or decimal with 2 fraction digits.',
		'range'		: '{0} must be beteen {1} and {2}.',
		'equals'	: '{0} must be same as {1}.',
		'lessthen'	: '{0} must be less than {1}.',
		'idcard'	: '{0} must be in proper ID format',

		'enchar'	: 'Letters, digits or underscore allowed only for {0}',
		'cnchar'	: '{0} must be Chinese charactors',
		'minlength'	: '{0} must contain more than {1} characters.',
		'maxlength'	: '{0} must contain less than {1} characters.'

}

// not working - Sigma.Msg.Validator['ja'] = Sigma.Msg.Validator.ja;
Sigma.Msg.Validator['default'] = Sigma.Msg.Validator.ja;