<%--
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
--%>

<%@ include file="/html/portlet/shopping/init.jsp" %>

<%
try {
	ShoppingCart cart = ShoppingUtil.getCart(renderRequest);

	ShoppingCartLocalServiceUtil.updateCart(cart.getUserId(), cart.getGroupId(), StringPool.BLANK, StringPool.BLANK, 0, false);
}
catch (Exception e) {
}
%>

<liferay-util:include page="/html/portlet/shopping/tabs1.jsp" servletContext="<%= application %>">
	<liferay-util:param name="tabs1" value="cart" />
</liferay-util:include>

<div class="alert alert-success">
	<liferay-ui:message key="thank-you-for-your-purchase" />
</div>

<%
ShoppingOrder order = null;

try {
	order = ShoppingOrderLocalServiceUtil.getOrder(ParamUtil.getLong(request, "orderId"));
}
catch (NoSuchOrderException nsoe) {
}
%>

<c:choose>
	<c:when test="<%= order != null %>">
		<liferay-ui:message key="your-order-number-is" /> <strong><%= HtmlUtil.escape(order.getNumber()) %></strong>. <liferay-ui:message key="you-will-receive-an-email-shortly-with-your-order-summary-and-further-details" />
	</c:when>
	<c:otherwise >
		<liferay-ui:message key="your-order-was-already-processed.-please-check-your-email-for-your-order-summary-and-further-details" />
	</c:otherwise>
</c:choose>

<portlet:renderURL var="continueShoppingURL">
	<portlet:param name="struts_action" value="/shopping/view" />
</portlet:renderURL>

<aui:button-row>
	<aui:button cssClass="btn-lg" href="<%= continueShoppingURL.toString() %>" value="continue-shopping" />
</aui:button-row>