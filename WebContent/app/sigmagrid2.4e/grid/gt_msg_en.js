//

if (!window.Sigma){
	window.Sigma={};
}
Sigma.Msg=Sigma.Msg || {};
SigmaMsg=Sigma.Msg;

Sigma.Msg.Grid = Sigma.Msg.Grid || {};

Sigma.Msg.Grid.en={
	LOCAL	: "EN",
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

	PAGE_STATE		: "#{1} - #{2} displayed,  #{3}page(s) #{4} record(s) totally.",
	PAGE_STATE_FULL	: "Page #{5}, #{1} - #{2} displayed,  #{3}page(s) #{4} record(s) totally.",

	SHADOWROW_FAILED: "", //"Relevant info not available",
	NEARPAGE_TITLE	: "",
	WAITING_MSG : 'Please wait...',

	NO_RECORD_UPDATE: "Nothing Modified",
	UPDATE_CONFIRM	: "Are you sure to save them?",
	NO_MODIFIED: "Nothing Modified",

	
	PAGE_BEFORE : '', //'Page',
	PAGE_AFTER : '',

	PAGESIZE_BEFORE :   '',
	PAGESIZE_AFTER :   ' Per Page',

	RECORD_UNIT : '',
	
	CHECK_ALL : 'Check All',

	COLUMNS_HEADER : 'Columns',

	DIAG_TITLE_FILTER : 'Filter Options',
	DIAG_NO_FILTER : 'No Filter',
	TEXT_ADD_FILTER	: "Add",
	TEXT_CLEAR_FILTER	: "Remove All",
	TEXT_OK	: "OK",
	TEXT_DEL : "Delete",
	TEXT_CANCEL	: "Cancel",
	TEXT_CLOSE	: "Close",
	TEXT_UP : "Up",
	TEXT_DOWN : "Down",

	NOT_SAVE : "Do you want to save the changes? \n Click \"Cancel\" to discard.",

	DIAG_TITLE_CHART  : 'Chart',

	CHANGE_SKIN : "Skins",

	STYLE_NAME_DEFAULT : "Classic",
	STYLE_NAME_PINK : "Pink",
	STYLE_NAME_VISTA : "Vista",
	STYLE_NAME_MAC : "Mac",

	MENU_FREEZE_COL : "Lock Columns",
	MENU_SHOW_COL : "Hide Columns",
	MENU_GROUP_COL : "Group Span",

	TOOL_RELOAD : "Refresh" ,
	TOOL_ADD : "Add" ,
	TOOL_DEL : "Delete" ,
	TOOL_SAVE : "Save" ,

	TOOL_PRINT : "Print" ,
	TOOL_XLS : "Export to xls" ,
	TOOL_PDF : "Export to pdf" ,
	TOOL_CSV : "Export to csv" ,
	TOOL_XML : "Export to xml",
	TOOL_FILTER : "Filter" ,
	TOOL_CHART : "Chart" ,
	
	// Start adding custom messages here
	INVALID_NUMBER : 'must be a number',
	INVALID_INPUT : 'invalid input',
	REQUIRED_FIELDS_NOT_POPULATED : 'Fill up all the required fields correctly',
	SAVED : 'It was registered',
	PUBLISHED : 'This sheet was published.',
	UNPUBLISHED : 'This sheet was unpublished.',
	CONFIRM_FINALIZE : 'Are you sure you want to finalize?',
	CONFIRM_INVISIBLE : 'Quantity has been set. Are you sure you want to clear?',
	ERROR_INVISIBLE : 'Unable to hide SKU. SKU has already quantity.',
	FINALIZED : 'This sheet was finalized',
	UNFINALIZED : 'This sheet was unfinalized',
	ORDER_CONFIRM_UNFINALIZE : 'Are you sure you want to unfinalize?\nThis will delete all data in Seller Allocation sheet.',
	SKU_MAX_LIMIT_EXCEEDED : 'Order limitation is #{1}.',
	BUYER_SKU_MAX_LIMIT_EXCEEDED : 'I exceed the number of the order upper limits. [the ordering possibility amount, {1}]',
	CONFIRM_DELETE : 'Are you sure you want to delete this SKU',
	UNIT_ORDER_ERROR1 : 'Cannot change unit of order. Buyer quantities contains decimal values.',
	UNIT_ORDER_ERROR2 : 'Cannot change unit of order. SKU Limit contains decimal value.',
	CONFIRM_LOCK : 'Are you sure you want to lock this page?',
	CONFIRM_UNLOCK : 'Are you sure you want to unlock this page?',
	LOCKED : 'This sheet was locked.',
	UNLOCKED : 'This sheet was unlocked.',
	CONFIRM_APPROVE : 'Are you sure you want to approve this sheet?',
	CONFIRM_UNAPPROVE : 'Are you sure you want to unapprove this sheet?',
	APPROVED : 'Received Sheet Approved.',
	UNAPPROVED : 'Received Sheet Unapproved.',
	SKU_DELETE_ERROR : 'Cannot delete SKU. SKU has quantities.',
	PRICE_WO_TAX : 'Total of Price Without Tax', 												
	PRICE_W_TAX : 'Total of Price With Tax',
	SELLING_PRICE : 'Total of Selling Price',
	PROFIT : 'Total of Profit',
	PROFIT_PERCENTAGE : 'Total of Profit Percentage',
	GT_PRICE_WO_TAX : 'Grand Total of Price Without Tax',
	GT_PRICE_W_TAX : 'Grand Total of Price With Tax',
	GT_SELLING_PRICE : 'Grand Total of Selling Price',
	GT_PROFIT : 'Grand Total of Profit',
	GT_PROFIT_PERCENTAGE : 'Grand Total of Profit Percentage',
	SKU_LIMIT_LESS_THAN_TOTAL_QTY : 'Cannot input SKU max limit less than #{1} total quantity.'

};

Sigma.Msg.Grid['default']=Sigma.Msg.Grid.en;


if (!Sigma.Msg.Validator){
	Sigma.Msg.Validator={ };
}

Sigma.Msg.Validator.en={

		'required'	: '{0#This field} is required.',
		'date'		: '{0#This field} must be in proper format ({1#YYYY-MM-DD}).',
		'time'		: '{0#This field} must be in proper format ({1#HH:mm}).',
		'datetime'	: '{0#This field} must be in proper format ({1#YYYY-MM-DD HH:mm}).',
		'email'		: '{0#This field} must be in proper email format.',
		'telephone'	: '{0#This field} must be in proper phone no format.',
		'number'	: '{0} must be a number.',
		'integer'	: '{0} must be an integer.',
		'float'		: '{0} must be integer or decimal.',
		'money'		: '{0} must be integer or decimal with 2 fraction digits.',
		'range'		: '{0} must be between {1} and {2}.',
		'equals'	: '{0} must be same as {1}.',
		'lessthen'	: '{0} must be less than {1}.',
		'idcard'	: '{0} must be in proper ID format',

		'enchar'	: 'Letters, digits or underscore allowed only for {0}',
		'cnchar'	: '{0} must be Chinese charactors',
		'minlength'	: '{0} must contain more than {1} characters.',
		'maxlength'	: '{0} must contain less than {1} characters.'

}

Sigma.Msg.Validator['default'] = Sigma.Msg.Validator.en;