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
 * Feb 18, 2010		pamela		
 */
package com.freshremix.webapp.controller.pmf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.sojo.interchange.Serializer;
import net.sf.sojo.interchange.json.JsonSerializer;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.freshremix.constants.SessionParamConstants;
import com.freshremix.model.PmfSkuList;
import com.freshremix.service.ProductMasterFileService;
import com.freshremix.ui.model.PmfForm;
import com.freshremix.util.PmfUtil;
import com.freshremix.util.SessionHelper;

/**
 * @author Pammie
 *
 */
public class PmfSavePmfSkusController extends SimpleFormController {
	ProductMasterFileService pmfService;

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		String json = request.getParameter("_gt_json");
		
		Serializer serializer = new JsonSerializer();
		PmfForm pmfForm = (PmfForm) serializer.deserialize(json, PmfForm.class);
		
		System.out.println("PMFForm " + pmfForm);
		System.out.println("Inserted " + pmfForm.getInsertedPmfSkus());
		System.out.println("Updated " + pmfForm.getUpdatedPmfSkus());
		System.out.println("Deleted " + pmfForm.getDeletedPmfSkus());
		
		Integer categoryId = Integer.valueOf(SessionHelper.getAttribute(request, "categoryId").toString());
		
		if(null == request.getSession().getAttribute("btnClicked")){
			//for SKU insert
			if (pmfForm.getInsertedPmfSkus().size()>0){
				for (int i=0;i<pmfForm.getInsertedPmfSkus().size();i++){
					PmfSkuList pmf = PmfUtil.toPmfSku(pmfForm.getInsertedPmfSkus().get(i));
					pmf.setCategoryId(categoryId);
					pmf.setSellerId((Integer)SessionHelper.getAttribute(request, SessionParamConstants.PMF_USER_PARAM));
					pmfService.insertPmfSkus(pmf);
				}
			}

			//for SKU update
			if (pmfForm.getUpdatedPmfSkus().size()>0){
				for (int i=0;i<pmfForm.getUpdatedPmfSkus().size();i++){
					PmfSkuList pmf = PmfUtil.toPmfSku(pmfForm.getUpdatedPmfSkus().get(i));
					pmfService.updatePmfSku(pmf);
				}
			}

			//for SKU delete
			if (pmfForm.getDeletedPmfSkus().size()>0){
				for (int i=0;i<pmfForm.getDeletedPmfSkus().size();i++){
					PmfSkuList pmf = PmfUtil.toPmfSku(pmfForm.getDeletedPmfSkus().get(i));
					pmfService.deletePmfSku(pmf.getSkuId(), pmf.getPmfId());
				}
			}
		}
		
		return super.onSubmit(request, response, command, errors);
	}

	public void setPmfService(ProductMasterFileService pmfService) {
		this.pmfService = pmfService;
	}
}
