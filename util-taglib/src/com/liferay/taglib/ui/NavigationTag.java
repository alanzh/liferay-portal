/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.PortletDisplayTemplateManagerUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.theme.NavItem;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Tibor Lipusz
 */
public class NavigationTag extends IncludeTag {

	public void setBulletStyle(String bulletStyle) {
		_bulletStyle = bulletStyle;
	}

	public void setDdmTemplateGroupId(long ddmTemplateGroupId) {
		_ddmTemplateGroupId = ddmTemplateGroupId;
	}

	public void setDdmTemplateKey(String ddmTemplateKey) {
		_ddmTemplateKey = ddmTemplateKey;
	}

	public void setDisplayStyleDefinition(String[] displayStyleDefinition) {
		_displayStyleDefinition = displayStyleDefinition;
	}

	public void setHeaderType(String headerType) {
		_headerType = headerType;
	}

	public void setIncludedLayouts(String includedLayouts) {
		_includedLayouts = includedLayouts;
	}

	public void setNestedChildren(boolean nestedChildren) {
		_nestedChildren = nestedChildren;
	}

	public void setPreview(boolean preview) {
		_preview = preview;
	}

	public void setRootLayoutLevel(int rootLayoutLevel) {
		_rootLayoutLevel = rootLayoutLevel;
	}

	public void setRootLayoutType(String rootLayoutType) {
		_rootLayoutType = rootLayoutType;
	}

	@Override
	protected void cleanUp() {
		_bulletStyle = "1";
		_displayStyleDefinition = null;
		_headerType = "none";
		_includedLayouts = "auto";
		_nestedChildren = true;
		_preview = false;
		_rootLayoutLevel = 1;
		_rootLayoutType = "absolute";
	}

	protected List<NavItem> getBranchNavItems(HttpServletRequest request)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		List<Layout> layoutAncestors = layout.getAncestors();

		NavItem navItem = new NavItem(request, layout, null);

		List<NavItem> branchNavItems = new ArrayList<>();

		branchNavItems.add(navItem);

		for (Layout layoutAncestor : layoutAncestors) {
			branchNavItems.add(0, new NavItem(request, layoutAncestor, null));
		}

		return branchNavItems;
	}

	protected String getDisplayStyle() {
		if (Validator.isNotNull(_ddmTemplateKey)) {
			return PortletDisplayTemplateManagerUtil.getDisplayStyle(
				_ddmTemplateKey);
		}

		return null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-ui:navigation:bulletStyle", _bulletStyle);
		request.setAttribute(
			"liferay-ui:navigation:displayStyle", getDisplayStyle());
		request.setAttribute(
			"liferay-ui:navigation:displayStyleGroupId", _ddmTemplateGroupId);
		request.setAttribute("liferay-ui:navigation:headerType", _headerType);
		request.setAttribute(
			"liferay-ui:navigation:includedLayouts", _includedLayouts);
		request.setAttribute(
			"liferay-ui:navigation:nestedChildren",
			String.valueOf(_nestedChildren));
		request.setAttribute(
			"liferay-ui:navigation:preview", String.valueOf(_preview));
		request.setAttribute(
			"liferay-ui:navigation:rootLayoutLevel", _rootLayoutLevel);
		request.setAttribute(
			"liferay-ui:navigation:rootLayoutType", _rootLayoutType);

		try {
			List<NavItem> branchNavItems = getBranchNavItems(request);

			request.setAttribute(
				"liferay-ui:navigation:navItems", branchNavItems);
		}
		catch (PortalException e) {
			_log.error(e);
		}
	}

	private static final String _PAGE = "/html/taglib/ui/navigation/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(NavigationTag.class);

	private String _bulletStyle = "1";
	private long _ddmTemplateGroupId;
	private String _ddmTemplateKey;
	private String[] _displayStyleDefinition;
	private String _headerType = "none";
	private String _includedLayouts = "auto";
	private boolean _nestedChildren = true;
	private boolean _preview;
	private int _rootLayoutLevel = 1;
	private String _rootLayoutType = "absolute";

}