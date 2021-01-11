/**
 * <B>(C) Copyright Freshremix Asia Software Corporation</B><BR>
 * <BR>
 * <B>Description:</B><BR>
 * Describe class or interface.<BR>
 * <BR>
 * <B>Known Bugs:</B>
 * none<BR>
 * <BR>
 * <B>History:</B>
 * <PRE><!-- Do not use tabs in the history table! Do not extend table width! -->
 * date       name            changes
 * ------------------------------------------------------------------------------
 * Jun 22, 2010		Pammie		
 */
package com.freshremix.constants;

/**
 * @author Pammie
 *
 */
public class UploadCsvMessages {
	public static final String SKU_UPLOAD_REQUIRED_FILENAME = "ファイル名が必須項目です。入力してください。";
	public static final String SKU_UPLOAD_ERROR_DATE = "日付が正しくありません。";
	public static final String SKU_UPLOAD_ERROR_LENGTH = "{0}文字以上は登録できません。";
	public static final String SKU_UPLOAD_ERROR_NUMBER = "桁数オーバーです。{0}桁以内で登録してください。";
	public static final String SKU_UPLOAD_ERROR_FILE = "データがありません。";
	public static final String SKU_UPLOAD_ERROR_CSV = "ファイルが正しくありません。";
	public static final String SKU_UPLOAD_ERROR_HEADER = "ファイルが正しくありません。";
	public static final String SKU_UPLOAD_ERROR_UOM = "発注単位は'{0}'または'{1}'を入力してください。";
	public static final String SKU_UPLOAD_ERROR_FIELDS = "項目数が正しくありません。";
	public static final String SKU_UPLOAD_ERROR_SHEET = "「Sheet Type」が正しくありません（{0}）。";
	public static final String SKU_UPLOAD_ERROR_CATEGORY = "「Category」は{0}または{1}または{2}を入力してください。";
	public static final String SKU_UPLOAD_ERROR_OPERATION = "「Operation Flag」は追加＝'{0}'、修正＝'{1}'、削除＝'{2}'、案内＝'{3}'を入力してください。";
	public static final String SKU_UPLOAD_ERROR_SKUIDBLANK = "商品追加時は商品コードを登録できません。";
	public static final String SKU_UPLOAD_ERROR_REQUIRED = "必須項目が入力されていません。";
	public static final String SKU_UPLOAD_ERROR_NOTPRODSHEETDATE = "アップロード対象の日付ではありません。";
	public static final String SKU_UPLOAD_ERROR_NOTPRODSHEETUSER = "売主担当者が正しくありません。";
	public static final String SKU_UPLOAD_ERROR_FINALIZATION = "「Finalization Flag」は{0}を入力してください。";
	public static final String SKU_UPLOAD_ERROR_NOSKU = "商品が見つかりません。";
	public static final String SKU_UPLOAD_ERROR_NOSKUGROUP = "商品グループが見つかりません。";
	public static final String SKU_UPLOAD_ERROR_CANNOT_EDIT_DELETE_MARKED = "同じ商品は編集できません（商品コード{0}）。";
	public static final String SKU_UPLOAD_ERROR_CANNOT_EDIT_EDIT_MARKED = "同じ商品は編集できません（商品コード{0}）。";
	public static final String SKU_UPLOAD_ERROR_CANNOT_DELETE_EDIT_MARKED = "同じ商品は編集できません（商品コード{0}）。";
	public static final String SKU_UPLOAD_ERROR_CANNOT_DELETE_DELETE_MARKED = "同じ商品は編集できません（商品コード{0}）。";
	public static final String SKU_UPLOAD_SUCCESS = "登録されました。";
	public static final String SKU_UPLOAD_ERROR_SKUGROUP_INCONSISTENT = "商品グループが正しくありません。";
	public static final String SKU_UPLOAD_ERROR_VISIBILITY = "DISPLAY_FLAGの値が正しくありません。";
	public static final String SKU_UPLOAD_ERROR_CANNOT_EDIT_PROPOSED = "バイヤー商品の店舗案内設定（DISPLAY Flag）を変更することはできません。";
	public static final String SKU_UPLOAD_ERROR_BUYERID = "指定の買主は紐付けがありません。";
	public static final String SKU_UPLOAD_ERROR_PRICE1 = "価格１が正しくありません";
	public static final String SKU_UPLOAD_ERROR_PRICE2 = "価格２が正しくありません";
	public static final String SKU_UPLOAD_ERROR_PRICE = "店着税抜が正しくありません";
}
