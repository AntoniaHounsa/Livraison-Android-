<%@ Control Language="C#" AutoEventWireup="false" CodeBehind="SprintBar.ascx.cs" Inherits="CDM.SD.ACE.WEB.SprintBar" %>

<!-- The SprintBar supports only IE 11+ -->
<!--[if gte IE 11]><!-->
  <style>
      .amc-minitoolbar {
        display: none;
        position: fixed;
        bottom: 0;
        right: 0;
        padding: 5px 5px 0;
        background-color: #f7f7f7;
        background-image: -moz-linear-gradient(top, #e4e4e4, #ffffff);
        background-image: -webkit-gradient(
          linear,
          0% 0%,
          0% 100%,
          from(#e4e4e4),
          to(#ffffff)
        );
        background-image: -o-linear-gradient(top, #e4e4e4, #ffffff);
        background: linear-gradient(to top, #e4e4e4, #ffffff);
        border-radius: 16px 0 0 0;
        z-index: 6000000;
      }

      .amc-toolbarreset * {
        -webkit-box-sizing: content-box;
        -moz-box-sizing: content-box;
        box-sizing: content-box;
        vertical-align: baseline;
      }

      .amc-toolbarreset {
        position: fixed;
        background-color: #f7f7f7;
        left: 0;
        right: 0;
        margin: 0;
        z-index: 6000000;
        font: 11px Verdana, Arial, sans-serif;
        text-align: left;
        color: #2f2f2f;
        background-image: -moz-linear-gradient(top, #e4e4e4, #ffffff);
        background-image: -webkit-gradient(
          linear,
          0% 0%,
          0% 100%,
          from(#e4e4e4),
          to(#ffffff)
        );
        background-image: -o-linear-gradient(top, #e4e4e4, #ffffff);
        background: linear-gradient(to top, #e4e4e4, #ffffff);
        bottom: 0;
        border-top: 1px solid #bbb;
        padding-left: 110px;
      }

      .amc-toolbarreset abbr {
        border-bottom: 1px dotted #000000;
        cursor: help;
      }

      .amc-toolbarreset span,
      .amc-toolbarreset div,
      .amc-toolbarreset td,
      .amc-toolbarreset th {
        font-size: 11px;
      }

      .amc-toolbarreset img {
        width: auto;
        display: inline;
      }

      .amc-toolbarreset table {
        border-collapse: collapse;
        border-spacing: 0;
        background-color: #000;
        margin: 0;
        padding: 0;
        border: 0;
        width: 100%;
        table-layout: auto;
      }

      .amc-toolbar-block {
        white-space: nowrap;
        color: #2f2f2f;
        display: block;
        min-height: 28px;
        border-bottom: 1px solid #e4e4e4;
        border-right: 1px solid #e4e4e4;
        padding: 0;
        float: left;
        cursor: default;
      }

      .amc-toolbar-block#disable-debug button {
        border: none;
      }

      .amc-toolbar-block span {
        display: inline-block;
      }

      .amc-toolbar-block .amc-toolbar-info-piece {
        line-height: 19px;
        margin-bottom: 5px;
      }

      .amc-toolbar-block .amc-toolbar-info-piece .amc-toolbar-status {
        padding: 0px 5px;
        border-radius: 5px;
        margin-bottom: 0px;
        vertical-align: top;
        position: absolute;
        top:2px;
        right: 0;
      }

      .amc-toolbar-block .amc-toolbar-info-piece:last-child {
        margin-bottom: 0;
      }

      .amc-toolbar-block .amc-toolbar-info-piece a,
      .amc-toolbar-block .amc-toolbar-info-piece abbr {
        color: #2f2f2f;
      }

      .amc-toolbar-block .amc-toolbar-info-piece b {
        display: inline-block;
        min-width: 110px;
        vertical-align: top;
      }

      .amc-toolbar-block .amc-toolbar-info-with-next-pointer:after {
        content: " :: ";
        color: #999;
      }

      .amc-toolbar-block .amc-toolbar-info-with-delimiter {
        border-right: 1px solid #999;
        padding-right: 5px;
        margin-right: 5px;
      }

      .amc-toolbar-block .amc-toolbar-info {
        display: none;
        position: absolute;
        background-color: #fff;
        border: 1px solid #bbb;
        padding: 9px 0;
        margin-left: -1px;
        bottom: 38px;
        border-bottom-width: 0;
        border-bottom: 1px solid #bbb;
        border-radius: 4px 4px 0 0;
      }

      .amc-toolbar-block .amc-toolbar-info:empty {
        visibility: hidden;
      }

      .amc-toolbar-block .amc-toolbar-status {
        display: inline-block;
        color: #fff;
        background-color: #666;
        padding: 3px 6px;
        border-radius: 3px;
        margin-bottom: 2px;
        vertical-align: middle;
        min-width: 6px;
        min-height: 13px;
      }

      .amc-toolbar-block .amc-toolbar-status abbr {
        color: #fff;
        border-bottom: 1px dotted black;
      }

      .amc-toolbar-block .amc-toolbar-status-green {
        background-color: #5e8014;
      }

      .amc-toolbar-block .amc-toolbar-status-red {
        background-color: #a33;
      }

      .amc-toolbar-block .amc-toolbar-status-yellow {
        background-color: #ffcc00;
        color: #000;
      }

      .amc-toolbar-block .amc-toolbar-status-black {
        background-color: #000;
      }

      .amc-toolbar-block .amc-toolbar-icon {
        display: block;
      }

      .amc-toolbar-block .amc-toolbar-icon > a,
      .amc-toolbar-block .amc-toolbar-icon > span {
        display: block;
        font-weight: normal;
        text-decoration: none;
        margin: 0;
        padding: 5px 8px;
        min-width: 20px;
        text-align: center;
        vertical-align: middle;
      }

      .amc-toolbar-block .amc-toolbar-icon > a,
      .amc-toolbar-block
        .amc-toolbar-icon
        > a:link
        .amc-toolbar-block
        .amc-toolbar-icon
        > a:hover {
        color: black !important;
      }

      .amc-toolbar-block .amc-toolbar-icon > a[href]:after {
        content: "";
      }

      .amc-toolbar-block .amc-toolbar-icon img,
      .amc-toolbar-block .amc-toolbar-icon svg {
        border-width: 0;
        vertical-align: middle;
      }

      .amc-toolbar-block .amc-toolbar-icon img + span,
      .amc-toolbar-block .amc-toolbar-icon svg + span {
        margin-left: 5px;
        margin-top: 2px;
      }

      .amc-toolbar-block .amc-toolbar-icon .amc-toolbar-status {
        border-radius: 12px;
        border-bottom-left-radius: 0;
        margin-top: 0;
      }

      .amc-toolbar-block .amc-toolbar-info-method {
        border-bottom: 1px dashed #ccc;
        cursor: help;
      }

      .amc-toolbar-block .amc-toolbar-info-method[onclick=""] {
        border-bottom: none;
        cursor: inherit;
      }

      .amc-toolbar-info-php {
      }

      .amc-toolbar-info-php-ext {
      }

      .amc-toolbar-info-php-ext .amc-toolbar-status {
        margin-left: 2px;
      }

      .amc-toolbar-info-php-ext .amc-toolbar-status:first-child {
        margin-left: 0;
      }

      .amc-toolbar-block a .amc-toolbar-info-piece-additional,
      .amc-toolbar-block a .amc-toolbar-info-piece-additional-detail {
        display: inline-block;
      }

      .amc-toolbar-block a .amc-toolbar-info-piece-additional:empty,
      .amc-toolbar-block a .amc-toolbar-info-piece-additional-detail:empty {
        display: none;
      }

      .amc-toolbarreset:hover {
        box-shadow: rgba(0, 0, 0, 0.3) 0 0 50px;
      }

      .amc-toolbar-block:hover {
        box-shadow: rgba(0, 0, 0, 0.35) 0 0 5px;
        border-right: none;
        margin-right: 1px;
        position: relative;
      }

      .amc-toolbar-block:hover .amc-toolbar-icon {
        background-color: #fff;
        border-top: 1px dotted #ddd;
        position: relative;
        margin-top: -1px;
        z-index: 10002;
      }

      .amc-toolbar-block:hover .amc-toolbar-info {
        display: block;
        min-width: -webkit-calc(100% + 2px);
        min-width: calc(100% + 2px);
        z-index: 10001;
        box-sizing: border-box;
        padding: 9px;
        line-height: 19px;
        /*max-width: 480px;*/
        max-height: 480px;
        word-wrap: break-word;
        overflow: hidden;
        overflow-y: auto;
      }

      .amc-toolbar-table .separator {
        position: relative;
        width: 10px;
      }
      .amc-toolbar-table .separator::before {
        content: "";
        border-left: 2px dotted grey;
        position: absolute;
        left: 0;
        height: 21px;
      }
      .amc-toolbar-render-cell {
        min-width: 100px;
      }
      /* Tooltip container */
      .amc-toolbar-table .amc-tooltip {
        position: relative;
        display: inline-block;
        background-color: #666;
        color: white;
        padding: 2px 7px;
        border-radius: 50%;
        float: right;
        margin-right: 10px;
      }
      .amc-toolbar-table .amc-tooltip a {
        color: white;
        text-decoration: none;
      }

      /* Tooltip text */
      .amc-toolbar-table .amc-tooltip .tooltiptext {
        visibility: hidden;
        width: 200px;
        background-color: #333;
        color: #fff;
        text-align: center;
        padding: 5px 2px;
        border-radius: 6px;
        max-width: 200px;
        text-overflow: ellipsis;
        overflow: hidden;

        /* Position the tooltip text */
        position: absolute;
        z-index: 9999;
        bottom: 125%;
        left: 50%;
        margin-left: -100px;

        /* Fade in tooltip */
        opacity: 0;
        transition: opacity 0.3s;
      }

      /* Tooltip arrow */
      .amc-toolbar-table .amc-tooltip .tooltiptext::after {
        content: "";
        position: absolute;
        top: 100%;
        left: 50%;
        margin-left: -7px;
        border-width: 7px;
        border-style: solid;
        border-color: #333 transparent transparent transparent;
      }

      /* Show the tooltip text when you mouse over the tooltip container */
      .amc-toolbar-table .amc-tooltip:hover .tooltiptext {
        visibility: visible;
        opacity: 1;
      }
      .amc-toolbar-table .amc-tooltip a::after {
        display: none;
      }

      .amc-toolbar-table th,
      .amc-toolbar-table td {
        padding: 0 4px;
        color: #2f2f2f;
        background-color: #fff;
        position: relative;
      }
      .amc-toolbar-table tr {
          border-bottom: 1px solid #ddd;
      }



      .amc-toolbar-table th {
        background-color: #eee;
      }
      .amc-toolbar-table .uri-cell-td {
        display: flex;
      }

      .amc-toolbar-table td a {
        display: flex;
        width: fit-content;
      }

      .amc-ajax-request-url {
        max-width: 300px;
        line-height: 9px;
        overflow: hidden;
        text-overflow: ellipsis;
      }

      .amc-toolbar-table-number {
        text-align: right;
      }

      .amc-ajax-request-error {
        color: #a33 !important;
      }

      .amc-ajax-request-loading {
        -webkit-animation: amc-blink 0.5s ease-in-out infinite;
        -o-animation: amc-blink 0.5s ease-in-out infinite;
        -moz-animation: amc-blink 0.5s ease-in-out infinite;
        animation: amc-blink 0.5s ease-in-out infinite;
      }

      @-webkit-keyframes amc-blink {
        0% {
          color: black;
        }

        50% {
          color: #bbb;
        }

        100% {
          color: black;
        }
      }

      @-moz-keyframes amc-blink {
        0% {
          color: black;
        }

        50% {
          color: #bbb;
        }

        100% {
          color: black;
        }
      }

      @-o-keyframes amc-blink {
        0% {
          color: black;
        }

        50% {
          color: #bbb;
        }

        100% {
          color: black;
        }
      }

      @keyframes amc-blink {
        0% {
          color: black;
        }

        50% {
          color: #bbb;
        }

        100% {
          color: black;
        }
      }

      /***** Override the setting when the toolbar is on the top *****/

      /***** Media query *****/
      @media screen and (max-width: 779px) {
        .amc-toolbar-block .amc-toolbar-icon > * > :first-child ~ * {
          display: none;
        }

        .amc-toolbar-block
          .amc-toolbar-icon
          > *
          > .amc-toolbar-info-piece-additional,
        .amc-toolbar-block
          .amc-toolbar-icon
          > *
          > .amc-toolbar-info-piece-additional-detail {
          display: none !important;
        }
      }

      @media screen and (min-width: 880px) {
        .amc-toolbar-block
          .amc-toolbar-icon
          a[href$="config"]
          .amc-toolbar-info-piece-additional {
          display: inline-block;
        }
      }

      @media screen and (min-width: 980px) {
        .amc-toolbar-block
          .amc-toolbar-icon
          a[href$="security"]
          .amc-toolbar-info-piece-additional {
          display: inline-block;
        }
      }

      @media screen and (max-width: 1179px) {
        .amc-toolbar-block
          .amc-toolbar-icon
          > *
          > .amc-toolbar-info-piece-additional {
          display: none;
        }
      }

      @media screen and (max-width: 1439px) {
        .amc-toolbar-block
          .amc-toolbar-icon
          > *
          > .amc-toolbar-info-piece-additional-detail {
          display: none;
        }
      }

      /***** Media query print: Do not print the Toolbar. *****/
      @media print {
        .amc-toolbar {
          display: none;
          visibility: hidden;
        }
      }

      /***** AMC content highlighting *****/
      .amc-highlightable {
        padding: 2px;
      }

      .amc-highlight {
        background-color: rgba(100, 155, 255, 0.3) !important;
      }
      .amc-toolbar-table .content-row{
          background-color: white;
      }
      .amc-toolbar-table .toggle-button {
        cursor: pointer;
        margin-right: 6px;
        display: inline-block;
        transition: transform 0.3s ease;
        font-size: 28px
      }
      .amc-toolbar-table .toggle-button.expanded {
        transform: rotate(90deg);
      }
      .amc-toolbar-table .content-row[data-hidden="true"] {
        display: none;
      }
      .amc-toolbar-table .indent {
        display: inline-block;
        width: calc(var(--level, 0) * 16px);
      }
    </style>
<script type="text/javascript">
    if (!("amc" in window)) {
        amc = (function (document) {
            var ensureToolbarUnicity = function (document) {
                var found = document.querySelectorAll("[data-amc-toolbar-unique]");
                var chosen = {};

                var key, element;
                for (var i = 0; i < found.length; i++) {
                    element = found[i];
                    key = element.getAttribute("data-amc-toolbar-unique");
                    if (chosen.hasOwnProperty(key)) {
                        element.parentNode.removeChild(element);
                    } else {
                        chosen[key] = element;
                    }
                }
            };

            var indexDropTargets = function (document) {
                var element, category, i;
                var result = {};
                var statusElements = document.querySelectorAll("[data-amc-toolbar-drop-status]");
                var infoElements = document.querySelectorAll("[data-amc-toolbar-drop-info]");

                for (i = 0; i < statusElements.length; i++) {
                    element = statusElements[i];
                    category = element.getAttribute("data-amc-toolbar-drop-status");
                    result[category] = { "status": element };
                }
                for (i = 0; i < infoElements.length; i++) {
                    element = infoElements[i];
                    category = element.getAttribute("data-amc-toolbar-drop-info");
                    result[category]["info"] = element;
                }
                return result;
            };

            var contentComparer = function (c1, c2) {
                return (c1 === c2) || (c1.uri === c2.uri && c1.version === c2.version);
            };

            var tagComparer = function (t1, t2) {
                if (t1.uri !== t2.uri || t1.order !== t2.order)
                    return false;

                if (t1.contents.length > t2.contents.length)
                    t2.contents = t1.contents;
                if (t1.contents.length < t2.contents.length)
                    t1.contents = t2.contents;

                return true;
            };

            var unknownRuleComparer = function (r1, r2) {
                return r1.toLowerCase() === r2.toLowerCase();
            };

            var liquidErrorsComparer = function (r1, r2) {
                return false;
            };

            var ruleComparer = function (r1, r2) {
                return r1.name.toLowerCase() === r2.name.toLowerCase();
            };

            var ruleSortByName = function(r1, r2) {
                var name1 = r1.name.toLowerCase();
                var name2 = r2.name.toLowerCase();
                if (name1 < name2) return -1;
                if (name1 > name2) return 1;
                return 0;
            }

            var nativeComparer = function (item1, item2) {
                return item1 === item2;
            };

            var dateTimeString = function (date) {
                const options = {
                    year: 'numeric',
                    month: '2-digit',
                    day: '2-digit',
                    hour: '2-digit',
                    minute: '2-digit',
                    second: '2-digit',
                    hour12: false
                };

                const formatter = new Intl.DateTimeFormat("en-EN", options);
                const parts = formatter.formatToParts(date);
                
                const dateStr = `${parts.find(p => p.type === 'year').value}-${parts.find(p => p.type === 'month').value}-${parts.find(p => p.type === 'day').value}T${parts.find(p => p.type === 'hour').value}:${parts.find(p => p.type === 'minute').value}:${parts.find(p => p.type === 'second').value}`;
                return dateStr.substring(0, 16)
            }

            var dateLongString = function (date) {
            
                return date.toLocaleString('en-EN', {
                    year: "numeric",
                    month: "short",
                    day: "2-digit",
                    hour: "2-digit",
                    minute: "2-digit"
                });
            };

            var mergeArrays = function (source, destination, comparer) {
                var i, j, sourceItem, destinationItem, add;

                for (i = 0; i < source.length; i++) {
                    sourceItem = source[i];
                    add = true;
                    for (j = 0; j < destination.length; j++) {
                        destinationItem = destination[j];
                        if (comparer(sourceItem, destinationItem)) {
                            add = false;
                            break;
                        }
                    }
                    if (add) {
                        destination.push(sourceItem);
                    }
                }
            };

            var mergeDebugData = function (debugData) {
                
                var result = { contents: [], tags: [], totalTimeMs: 0, contentDisplayDate: "", changeDates: [], unknownRules: [], liquidErrors: [], rules: [], renders: [], contentDebugTree: [], componentDebugTree: [], loadedComponents: [] };
                var data, i;
                for (i = 0; i < debugData.length; i++) {
                    data = debugData[i];

                    mergeArrays(data.contents, result.contents, contentComparer);
                    mergeArrays(data.tags, result.tags, tagComparer);
                    result.totalTimeMs += data.totalTimeMs;
                    result.contentDisplayDate = data.contentDisplayDate;
                    mergeArrays(data.changeDates, result.changeDates, nativeComparer);
                    mergeArrays(data.unknownRules, result.unknownRules, unknownRuleComparer);
                    mergeArrays(data.rules, result.rules, ruleComparer);
                    mergeArrays(data.liquidErrors, result.liquidErrors, liquidErrorsComparer);
                    result.liquidErrorsCount = data.liquidErrorsCount;
                    result.admin = data.admin;
                    result.renders = data.renders;
                    result.contentDebugTree = [...result.contentDebugTree, ...data.contentDebugTree];
                    result.componentDebugTree = [...result.componentDebugTree, ...data.componentDebugTree];
                    result.loadedComponents = [...result.loadedComponents, ...data.loadedComponents];
                }
                result.rules.sort(ruleSortByName);
                result.changeDates.sort();

                return result;
            };

            var formatToolbarStatus = function (element, text, isAlert) {
                var colorClass = isAlert ? "amc-toolbar-status-yellow" : "amc-toolbar-status-green";
                element.classList.add(colorClass);
                element.textContent = text;
            };

            var createChangeDateOption = function (document, start, nextStart, selected) {
                var end, dateOption;
                if (nextStart !== null) {
                    end = new Date(nextStart.getTime());
                    end.setDate(nextStart.getDate());
                }
                var dateOption = document.createElement("option");
                dateOption.value = dateTimeString(start);
                dateOption.textContent = nextStart !== null ?
                    "From " + dateLongString(start) + " to " + dateLongString(end) :
                    "From " + dateLongString(start);

                    console.log(start, selected)

                if (selected)
                    dateOption.selected = true;

                return dateOption;
            };

            //var highlight = function (e) {
            //    var contentUri = e.currentTarget.getAttribute("data-amc-toolbar-content-uri");
            //    var contentDiv = document.querySelectorAll("[data-amc-content-uri='" + contentUri + "']")[0];
            //    contentDiv.classList.add("amc-highlight");
            //};
            //var unhighlight = function (e) {
            //    var contentUri = e.currentTarget.getAttribute("data-amc-toolbar-content-uri");
            //    var contentDiv = document.querySelectorAll("[data-amc-content-uri='" + contentUri + "']")[0];
            //    contentDiv.classList.remove("amc-highlight");
            //};

            var parseQueryString = function(search) {
                var result = {};

                if (search.length === 0 || search[0] !== '?')
                    return result;

                var params = search.substring(1).split("&");

                var splitParam;
                for (var i = 0; i < params.length; i++) {
                    splitParam = params[i].split("=", 2);
                    result[splitParam[0]] = splitParam[1];
                }

                return result;
            };

            var generateQueryString = function(params) {
                var result = "";
                var first = true;
                for (var key in params) {
                    if (first)
                        first = false;
                    else
                        result += "&";

                    if (params.hasOwnProperty(key))
                        result += key + "=" + params[key];
                }
                return result;
            };

            var timeTravel = function (e) {
                var query = parseQueryString(window.location.search);
                query["_amc-display-date"] = e.target.value;
                window.location.search = generateQueryString(query);
            };

            var evaluatedRules = {};

            var setRuleTrue = function(ruleName, params) {
                // If the rule is not used in content or render
                if (evaluatedRules[ruleName.toLowerCase()] === undefined)
                    return;
                if (evaluatedRules[ruleName.toLowerCase()].result) {
                    return;
                } else if (ruleName in params && params[ruleName] === "0") {
                    delete params[ruleName];
                } else {
                    params[ruleName] = "1";
                }
            };

            var setRuleFalse = function(ruleName, params) {
                // If the rule is not used in content or render
                if (evaluatedRules[ruleName.toLowerCase()] === undefined)
                    return;
                if (!evaluatedRules[ruleName.toLowerCase()].result) {
                    return;
                } else if (ruleName in params && params[ruleName] === "1") {
                    delete params[ruleName];
                } else {
                    params[ruleName] = "0";
                }
            };

            var exclusiveRuleGroups = [ "age", "branch", "regiondir", "fede", "oldfede", "market", "submarket", "branchtype", "userspace", "subportf", "brand", "channel", "language", "space", "environment", "badcontract" ];

            var authentifiedRuleGroups = ["age", "branchtype", "badcontract"];
            var recognizedRuleGroups = [ "branch", "regiondir", "fede", "oldfede", "market", "submarket", "userspace", "subportf" ];

            var simulateRule = function(e) {
                var ruleName = e.currentTarget.getAttribute("data-amc-ruleName");
                var query = parseQueryString(window.location.search);
                var rulePrefix = ruleName.split("_")[0].toLowerCase();
                if (e.currentTarget.checked) {
                    if (exclusiveRuleGroups.indexOf(rulePrefix) !== -1) {
                        for (var key in evaluatedRules) {
                            if (key.startsWith(rulePrefix) && ruleName.toLowerCase() !== key && evaluatedRules[key].result === true) {
                                setRuleFalse(evaluatedRules[key].name, query);
                            }
                        }
                        if (authentifiedRuleGroups.indexOf(rulePrefix) !== -1) {
                            setRuleTrue("User_Authenticated", query);
                        }
                        if (recognizedRuleGroups.indexOf(rulePrefix) !== -1 && evaluatedRules["user_authenticated"] !== undefined && !evaluatedRules["user_authenticated"].result) {
                            setRuleTrue("User_Client", query);
                        }
                    } else {
                        if (ruleName.toLowerCase() === "user_patrimonial") {
                            setRuleTrue("User_Authenticated", query);
                        } else if ((ruleName.toLowerCase() === "user_border" || ruleName.toLowerCase() === "user_familygroup" || ruleName.toLowerCase() === "user_resident" || ruleName.toLowerCase() === "user_hascustomermanager")
                                    && evaluatedRules["user_authenticated"] !== undefined && !evaluatedRules["user_authenticated"].result) {
                            setRuleTrue("User_Client", query);
                        }
                    }
                    //This method adds the rule in the query string if it is not already in
                    setRuleTrue(ruleName, query);
                } else {
                    if (ruleName.toLowerCase() === "user_authenticated") {
                        //Remove rules that need to be authentified
                        for (var authRule in evaluatedRules) {
                            if (authentifiedRuleGroups.indexOf(authRule.split("_")[0].toLowerCase()) !== -1 || authRule.toLowerCase() === "user_patrimonial") {
                                setRuleFalse(evaluatedRules[authRule].name, query);
                            }
                        }
                    }
                    if (ruleName.toLowerCase() === "user_client") {
                        for (var rule in evaluatedRules) {
                            if ((recognizedRuleGroups.indexOf(rule.split("_")[0].toLowerCase()) !== -1 || rule.toLowerCase() === "user_border"
                                || rule.toLowerCase() === "user_familygroup" || rule.toLowerCase() === "user_resident") && !evaluatedRules["user_authenticated"].result) {
                                setRuleFalse(evaluatedRules[rule].name, query);
                            }
                        }
                    }
                    //if rule is in query string, it removes it
                    setRuleFalse(ruleName, query);
                }
                var qs = generateQueryString(query);
                if (qs === "") {
                    window.location.href = window.location.href.split("?", 2)[0];
                } else {
                    window.location.search = qs;
                }
            };
            const createInfoPiece = function (document) {
                const result = document.createElement("div");
                result.className = "amc-toolbar-info-piece";
                return result;
            };
            const backOfficeLinkElt = function (document, element, html, type) {
                const r = document.createElement("a");
                r.href = `<%:BackOfficeBaseUrl()%>type=${type}&uri=${element.uri}&version=${element.version}`;
                r.target = "_blank";
                r.innerHTML = html;
                return r;
            };
            const createRenderCell = function (document, content) {
                contentRenderCell = document.createElement("td");
                if (!content.render) {
                    return contentRenderCell;
                }
                if (content.render.uri === "") {
                    return contentRenderCell;
                }

                const url = backOfficeLinkElt(
                    document,
                    content.render,
                    "",
                    "rendering"
                );
                contentRenderCell.innerHTML = `<div class='amc-tooltip'> <a href="${url.href}" target=_blank > R </a> <span class='tooltiptext'> ${content.render.uri}</span> </div>`;
                return contentRenderCell;
            };

            function buildNewTree(contentDebugTree, contents, renders) {
                if (!contentDebugTree || contentDebugTree.length == 0) return contents;
                const res = [];
                contentDebugTree.forEach((item) => {
                    const content = contents.find(
                        (c) => c.uri.toLowerCase() === item.contentUri.toLowerCase()
                    );
                    const render = renders.find(
                        (r) => r.uri.toLowerCase() === item.renderUri.toLowerCase()
                    );
                    if (content) {
                        const newContent = { ...content, render, children: [], parentContentUri: item.parentContentUri };
                        res.push(newContent);
                    }
                });
                contentDebugTree.forEach((item) => {
                    if (item.parentContentUri) {
                        const parent = res.find(x => x.uri.toLowerCase() == item.parentContentUri.toLowerCase());
                        let child;
                        if (item.renderUri) {
                            child = res.find(x => x.uri.toLowerCase() == item.contentUri.toLowerCase() && x.render?.uri.toLowerCase() == item.renderUri.toLowerCase())
                        } else {
                            child = res.find(x => x.uri.toLowerCase() == item.contentUri.toLowerCase())
                        }                        
                        if (parent && child) parent.children.push(child);
                    }
                });
                return res.filter((item) => item.parentContentUri == "");
            }

            function buildComponentTree(componentDebugTree, loadedComponents) {
                if (!componentDebugTree || componentDebugTree.length == 0) return [];
                const res = [];
                                    console.log("componentDebugTree in build componentTree ", componentDebugTree)

                // First pass: create all components with empty children
                componentDebugTree.forEach((item) => {
                    const componentLoaded = loadedComponents.find(lc => lc.uri === item.ComponentUri);
                    const sizeIndex = componentLoaded ? componentLoaded.sizeIndex : 0;
                    
                    const newComponent = {
                        uri: item.ComponentUri,
                        sizeIndex: sizeIndex,
                        parentContentUri: item.ParentContentUri,
                        parentComponentUri: item.ParentComponentUri,
                        parentListName: item.ParentListName,
                        isInList: item.ParentListName !== null && item.ParentListName !== undefined && item.ParentListName !== '',
                        children: [],
                        level: 0
                    };
                    res.push(newComponent);
                });

                // Group components by list name AND parent component
                const componentsByList = {};
                res.forEach((component) => {
                    if (component.isInList && component.parentListName) {
                        // Key is the parent component URI (or content URI if no parent component)
                        const parentKey = component.parentComponentUri || component.parentContentUri;
                        if (!componentsByList[parentKey]) {
                            componentsByList[parentKey] = {};
                        }
                        if (!componentsByList[parentKey][component.parentListName]) {
                            componentsByList[parentKey][component.parentListName] = [];
                        }
                        componentsByList[parentKey][component.parentListName].push(component);
                    }
                });

                // Second pass: build parent-child relationships
                componentDebugTree.forEach((item) => {
                    if (item.ParentComponentUri) {
                        // Component inside another component
                        const parent = res.find(x => x.uri.toLowerCase() === item.ParentComponentUri.toLowerCase());
                        const child = res.find(x => x.uri.toLowerCase() === item.ComponentUri.toLowerCase());
                        
                        // Only add to parent if NOT in a list (lists will be handled separately)
                        if (parent && child && !child.isInList) {
                            parent.children.push(child);
                            child.level = parent.level + 1;
                        }
                    } else if (item.ParentContentUri) {
                        // Component directly inside a content
                        // Level will be set when rendering
                    }
                });

                // Add list groups as children of their parent component or content
                Object.keys(componentsByList).forEach(parentKey => {
                    // Try to find parent component first
                    const parentComponent = res.find(x => x.uri.toLowerCase() === parentKey.toLowerCase());
                    
                    if (parentComponent) {
                        // List inside a component
                        Object.keys(componentsByList[parentKey]).forEach(listName => {
                            const listComponents = componentsByList[parentKey][listName];
                            const listGroup = {
                                uri: "__LIST__" + listName,
                                sizeIndex: 0,
                                parentContentUri: listComponents[0].parentContentUri,
                                parentComponentUri: parentComponent.uri,
                                parentListName: listName,
                                isInList: false,
                                isListGroup: true,
                                children: listComponents,
                                level: parentComponent.level + 1
                            };
                            res.push(listGroup);
                            parentComponent.children.push(listGroup);
                            
                            // Mark list components as having a parent (the list group)
                            // so they won't appear as root components
                            listComponents.forEach(component => {
                                component.parentComponentUri = listGroup.uri;
                            });
                        });
                    } else {
                        // List directly inside a content (no parent component)
                        // We need to add list groups to the root components list
                        Object.keys(componentsByList[parentKey]).forEach(listName => {
                            const listComponents = componentsByList[parentKey][listName];
                            const listGroup = {
                                uri: "__LIST__" + listName,
                                sizeIndex: 0,
                                parentContentUri: listComponents[0].parentContentUri,
                                parentComponentUri: null,
                                parentListName: listName,
                                isInList: false,
                                isListGroup: true,
                                children: listComponents,
                                level: 1
                            };
                            res.push(listGroup);
                            
                            // Mark list components as having a parent (the list group)
                            // so they won't appear as root components
                            listComponents.forEach(component => {
                                component.parentComponentUri = listGroup.uri;
                            });
                        });
                    }
                });

                // Find root components (not inside other components)
                // This will now only return the list groups for lists at content root
                return res.filter((item) => !item.parentComponentUri);
            }

            function addContentsTable(target, contents, renders, contentDebugTree, componentDebugTree, loadedComponents) {
                const infoPiece = createInfoPiece(document);
                const parsedTree = buildNewTree(contentDebugTree, contents, renders);
                const componentTree = buildComponentTree(componentDebugTree, loadedComponents);
                const table = document.createElement("table");
                table.classList.add("amc-toolbar-table");
                if (contentDebugTree && contentDebugTree.length > 0) {
                    table.innerHTML =
                        "<thead><tr><th>URI (version)<\/th><th>Render<\/th><th>Size<\/th></tr></thead>";
                } else {
                    table.innerHTML =
                        "<thead><tr><th>URI (version)<\/th><th>Size<\/th></tr></thead>";
                }

                const tbody = document.createElement("tbody");

                table.appendChild(tbody);
                target.appendChild(table);

                function toggleChildrenVisibility(parentUri, expanded) {
                    const rows = target.querySelectorAll(
                        `.content-row[data-parent-uri="${parentUri}"]`
                    );
                    rows.forEach((row) => {
                        if (expanded) {
                            row.removeAttribute("data-hidden");
                        } else {
                            row.setAttribute("data-hidden", "true");
                        }
                        const childUri = row.getAttribute("data-uri");
                        const childToggle = row.querySelector(".toggle-button");
                        if (!expanded && childToggle?.classList.contains("expanded")) {
                            childToggle.classList.remove("expanded");
                            toggleChildrenVisibility(childUri, false);
                        }
                    });
                }

                function createContentRow(content, level = 0, parentUri = null, loadedComponents = [], componentTree = []) {
                    const row = document.createElement("tr");
                    row.setAttribute("data-amc-toolbar-content-uri", content.uri);

                    row.classList.add("content-row");
                    row.setAttribute("data-uri", content.uri);
                    if (parentUri) {
                        row.setAttribute("data-parent-uri", parentUri);
                        row.setAttribute("data-hidden", "true");
                    }

                    const uriCell = document.createElement("td");
                    uriCell.classList.add("uri-cell-td");

                    const indent = document.createElement("span");
                    indent.className = "indent";
                    indent.style.setProperty("--level", level);
                    uriCell.appendChild(indent);

                    if (content.children?.length) {
                        const toggle = document.createElement("span");
                        toggle.className = "toggle-button";
                        toggle.textContent = "▸";
                        toggle.addEventListener("click", () => {
                            const isExpanded = toggle.classList.toggle("expanded");
                            toggle.textContent = isExpanded ? "▾" : "▸";
                            toggleChildrenVisibility(content.uri, isExpanded);
                        });
                        uriCell.appendChild(toggle);
                    } else {
                        const spacer = document.createElement("span");
                        spacer.style.display = "inline-block";
                        spacer.style.width = "29px";
                        uriCell.appendChild(spacer);
                    }

                    const label = document.createElement("span");

                    label.appendChild(
                        backOfficeLinkElt(
                            document,
                            content,
                            `${content.uri} (v${content.version})`,
                            "content"
                        )
                    );
                    uriCell.appendChild(label);
                    row.appendChild(uriCell);

                    if (contentDebugTree && contentDebugTree.length > 0) {
                        row.appendChild(createRenderCell(document, content));
                    }

                    const sizeCell = document.createElement("td");
                    sizeCell.className = "amc-toolbar-table-number";
                    const sizeSpan = document.createElement("span");
                    sizeSpan.textContent = content.sizeIndex;
                    sizeSpan.className = "amc-toolbar-status";
                    if (content.sizeIndex >= 100)
                        sizeSpan.classList.add("amc-toolbar-status-red");
                    else if (content.sizeIndex >= 80)
                        sizeSpan.classList.add("amc-toolbar-status-yellow");
                    else sizeSpan.classList.add("amc-toolbar-status-green");
                    sizeCell.appendChild(sizeSpan);
                    row.appendChild(sizeCell);

                    tbody.appendChild(row);

                    content.children?.forEach((child) =>
                        createContentRow(child, level + 1, content.uri, loadedComponents, componentTree)
                    );
                    // Add components for this content
                    if (componentTree && componentTree.length > 0) {
                        const contentComponents = componentTree.filter(c => c.parentContentUri === content.uri && !c.parentComponentUri);
                        contentComponents.forEach((component) => {
                            createComponentRow(tbody, component, level + 1, content.uri, loadedComponents, componentTree);
                        });
                    }
                }

               
                parsedTree.forEach((root) => createContentRow(root, 0, null, loadedComponents, componentTree));

                if (contentDebugTree && contentDebugTree.length > 0) {
                    const contentTableTitle = document.createElement("span");
                    contentTableTitle.innerHTML = `${contents.length} contents loaded - ${contentDebugTree.length} contents used or displayed`;
                    contentTableTitle.style.fontWeight = "bold";
                    infoPiece.appendChild(contentTableTitle);
                }
                if (componentTree && componentTree.length > 0) {
                    const componentTableTitle = document.createElement("span");
                    componentTableTitle.innerHTML = `<br/><strong> - ${componentTree.length} components loaded (including ${componentDebugTree.length - componentTree.length} nested)</strong>`;
                    componentTableTitle.style.fontWeight = "bold";
                    infoPiece.appendChild(componentTableTitle);
                }

                table.appendChild(tbody);
                infoPiece.appendChild(table);
                target.appendChild(infoPiece);
            }

                function createComponentRow(tbody, component, level, parentUri, loadedComponents, componentTree = []) {
                    const row = document.createElement("tr");
                    row.classList.add("content-row");
                    row.setAttribute("data-uri", component.uri);
                    row.setAttribute("data-parent-uri", parentUri);
                    row.setAttribute("data-hidden", "false");

                    const uriCell = document.createElement("td");
                    uriCell.classList.add("uri-cell-td");

                    const indent = document.createElement("span");
                    indent.className = "indent";
                    indent.style.setProperty("--level", level);
                    uriCell.appendChild(indent);

                    // Check if this is a component list group
                    if (component.isListGroup) {
                        console.log("isListGroup")
                        // Component list group - display as a group header
                        const toggle = document.createElement("span");
                        toggle.className = "toggle-button";
                        toggle.textContent = "▸";
                        toggle.addEventListener("click", () => {
                            const isExpanded = toggle.classList.toggle("expanded");
                            toggle.textContent = isExpanded ? "▾" : "▸";
                            toggleChildrenVisibility(component.uri, isExpanded);
                        });
                        uriCell.appendChild(toggle);

                        const label = document.createElement("span");
                        label.innerHTML = `<span style="color: #999; font-weight: bold;">[Liste: ${component.parentListName}]</span> (${component.children.length} composants)`;
                        uriCell.appendChild(label);
                    } else {
                        // Regular component
                        if (component.children && component.children.length > 0) {
                            const toggle = document.createElement("span");
                            toggle.className = "toggle-button";
                            toggle.textContent = "▸";
                            toggle.addEventListener("click", () => {
                                const isExpanded = toggle.classList.toggle("expanded");
                                toggle.textContent = isExpanded ? "▾" : "▸";
                                toggleChildrenVisibility(component.uri, isExpanded);
                            });
                            uriCell.appendChild(toggle);
                        } else {
                            const spacer = document.createElement("span");
                            spacer.style.display = "inline-block";
                            spacer.style.width = "29px";
                            uriCell.appendChild(spacer);
                        }

                        const label = document.createElement("span");
                        label.innerHTML = `<span style="color: #666;">[Component]</span> ${component.uri}`;
                        uriCell.appendChild(label);
                    }

                    row.appendChild(uriCell);

                    const sizeCell = document.createElement("td");
                    sizeCell.className = "amc-toolbar-table-number";
                    const sizeSpan = document.createElement("span");
                    sizeSpan.textContent = component.sizeIndex;
                    sizeSpan.className = "amc-toolbar-status";
                    if (component.sizeIndex >= 100)
                        sizeSpan.classList.add("amc-toolbar-status-red");
                    else if (component.sizeIndex >= 80)
                        sizeSpan.classList.add("amc-toolbar-status-yellow");
                    else sizeSpan.classList.add("amc-toolbar-status-green");
                    sizeCell.appendChild(sizeSpan);
                    row.appendChild(sizeCell);
                    tbody.appendChild(row);

                    // Add nested components (including component lists)
                    if (component.children && component.children.length > 0) {
                        component.children.forEach((childComponent) => {
                            createComponentRow(tbody, childComponent, level + 1, component.uri, loadedComponents, componentTree);
                        });
                    }
                }



            document.addEventListener("DOMContentLoaded", function () {
                var i, j, element;

                ensureToolbarUnicity(document);

                var drop = indexDropTargets(document);
                console.log("amc.debugData ", amc.debugData)
                var data = mergeDebugData(amc.debugData);

                // Format contents
                formatToolbarStatus(drop.contents.status, data.contents.length + " contents loaded", data.contents.length > <%:CONTENT_ALERT_LIMIT%>);

                var contentsNode = drop.contents.info;
                addContentsTable(contentsNode, data.contents, data.renders, data.contentDebugTree, data.componentDebugTree, data.loadedComponents);

                // Format tags
                formatToolbarStatus(drop.tags.status, data.tags.length + " tags", data.tags.length > <%:TAG_ALERT_LIMIT%>);

                var tagsNode = drop.tags.info;
                var tag, tagInfoPiece;
                for (i = 0; i < data.tags.length; i++) {
                    tag = data.tags[i];
                    tagInfoPiece = createInfoPiece(document);
                    var tagLink = "<a href=\"<%:BackOfficeBaseUrl()%>type=tag&uri=" + tag.uri + "\" target=\"_blank\" style=\"font: bold 11px Verdana, Arial, sans-serif;\">" + tag.uri + "</a>";
                    tagInfoPiece.innerHTML = "<span style=\"font-weight: bold;\">&lt;taglist uri=\"" + tagLink + "\" order=\"" + tag.order.toLowerCase() + "\"/&gt; " + tag.contents.length + " contents" + "</span>";
                    tagsNode.appendChild(tagInfoPiece);

                    addContentsTable(tagsNode, tag.contents, []);
                }

                // Format timing
                formatToolbarStatus(drop.timing.status, data.totalTimeMs + " ms", data.totalTimeMs > <%:TIME_LIMIT_MS%>);
                formatToolbarStatus(drop.timing.info, data.totalTimeMs + " ms", data.totalTimeMs > <%:TIME_LIMIT_MS%>);

                // Format dates
                var contentDisplayDate = new Date(data.contentDisplayDate);
                if (data.changeDates.length === 0) {
                    document.getElementById("changeDateDiv").style.display = "none";
                }
                else {
                    formatToolbarStatus(drop.changeDate.status, (data.changeDates.length + 1) + " time periods", false);
                }

                var dateSelector, previousDate, nextDate;
                dateSelector = drop.changeDate.info;
                previousDate = new Date();
                previousDate.setHours(0, 0, 0, 0)
                console.log(contentDisplayDate)
                for (i = 0; i < data.changeDates.length; i++)
                {
                    nextDate = new Date(data.changeDates[i]);
                    dateSelector.add(createChangeDateOption(document, previousDate, nextDate, contentDisplayDate >= previousDate && contentDisplayDate < nextDate));

                    previousDate = nextDate;
                }
                dateSelector.add(createChangeDateOption(document, previousDate, null, contentDisplayDate >= previousDate));
                dateSelector.addEventListener("change", timeTravel);

                // Format unknown rules
                if (data.unknownRules.length === 0) {
                    document.getElementById("unknownRulesDiv").style.display = "none";
                }
                else {
                    drop.unknownRules.status.classList.add("amc-toolbar-status-red");
                    drop.unknownRules.status.textContent = data.unknownRules.length + " unknown rules";
                }

                var unknownRule, unknownRuleDiv;
                for (i = 0; i < data.unknownRules.length; i++) {
                    unknownRule = data.unknownRules[i];
                    unknownRuleDiv = createInfoPiece(document);
                    unknownRuleDiv.innerHTML = unknownRule;
                    drop.unknownRules.info.appendChild(unknownRuleDiv);
                }

                //Format rules
                formatToolbarStatus(drop.rules.status, data.rules.length + " rules", data.rules.length > <%:RULES_LIMIT%>);

                var rule, ruleDiv, ruleLabel, ruleCheckbox;
                for (i = 0; i < data.rules.length; i++) {
                    rule = data.rules[i];
                    ruleDiv = createInfoPiece(document);

                    ruleLabel = document.createElement("label");

                    ruleCheckbox = document.createElement("input");
                    ruleCheckbox.type = "checkbox";
                    ruleCheckbox.setAttribute("data-amc-ruleName", rule.name);

<%
    //Initialize checkboxes with valid rules
%>
                    if (rule.result) {
                        ruleCheckbox.setAttribute("checked", "");
                    }
                    ruleCheckbox.addEventListener("click", simulateRule);

                    ruleLabel.appendChild(ruleCheckbox);
                    ruleLabel.appendChild(document.createTextNode(rule.name));

                    ruleDiv.appendChild(ruleLabel);

                    drop.rules.info.appendChild(ruleDiv);

                    evaluatedRules[rule.name.toLowerCase()] = rule;
                }

                // Format liquid errors
                if (data.liquidErrors.length === 0) {
                    document.getElementById("liquidErrorsDiv").style.display = "none";
                }
                else {
                    var errorString = "errors";
                    if (data.liquidErrors.length === 1)
                        errorString = "error";

                    drop.liquidErrors.status.classList.add("amc-toolbar-status-red");
                    drop.liquidErrors.status.textContent = data.liquidErrorsCount + " " + errorString + " in the render";
                }

                var liquidError, liquidErrorDiv;
                for (i = 0; i < data.liquidErrors.length; i++) {
                    liquidError = data.liquidErrors[i];
                    liquidErrorDiv = createInfoPiece(document);
                    liquidErrorDiv.innerHTML = liquidError;
                    drop.liquidErrors.info.appendChild(liquidErrorDiv);
                }

                //Admin
                if (data.admin !== "True") {
                    document.getElementById("adminDiv").style.display = "none";
                }
                

            });

            return {
                debugData: [],
            };
        })(document);
    }
    amc.debugData.push({
        "contentDebugTree": [
<% foreach (var content in DebugInfo.DebugContentTree)
   { %>
            { "contentUri": "<%:content.ContentUri%>", "renderUri": "<%:content.RenderUri%>", "parentContentUri": "<%:content.ParentContentUri%>" },
<% } %>
        ],
        "componentDebugTree": [
<% foreach (var component in DebugInfo.DebugComponentTree)
   { %>
            { "ComponentUri": "<%:component.ComponentUri%>", "ParentContentUri": "<%:component.ParentContentUri%>", "ParentComponentUri": "<%:component.ParentComponentUri%>", "ParentListName": "<%:component.ParentListName%>"},
<% } %>
        ],
        "loadedComponents": [
<% foreach (var component in DebugInfo.LoadedComponents)
   { %>
            { "uri": "<%:component.Uri%>", "sizeIndex": <%:component.SizeIndex%> },
<% } %>
        ],

        "contents": [
<% foreach (var content in DebugInfo.LoadedContents)
   { %>
            { "uri": "<%:content.Uri%>", "version": "<%:content.Version%>", "sizeIndex": <%:content.SizeIndex%>},
<% } %>
        ],
        "renders": [
<% foreach (var render in DebugInfo.LoadedRenders)
   { %>
            { "uri": "<%:render.Uri%>", "version": "<%:render.Version%>", "parentContentUri": "<%:render.ParentElementUri%>" },
<% } %>
        ],
        "tags": [
<% foreach (var tag in DebugInfo.LoadedTags)
   { %>
            { "uri": "<%:tag.Key.Uri%>", "order": "<%:tag.Key.Order%>", "contents": [
<%     foreach (var content in tag.SelectedContents)
       { %>
                { "uri": "<%:content.Uri%>", "version": "<%:content.Version%>", "sizeIndex": <%:content.SizeIndex%> },
<%     } %>
            ], },
<% } %>
        ],
        "totalTimeMs": <%:(int)DebugInfo.GlobalRenderTime.TotalMilliseconds%>,
        "contentDisplayDate": "<%:DebugInfo.DisplayDate.ToString("yyyy-MM-dd HH:mm")%>",
        "changeDates": [
<% foreach (var date in ChangeDates)
   { %>
            "<%:date.ToString("yyyy-MM-dd HH:mm")%>",
<% } %>
        ],
        "unknownRules": [
<% foreach (var rule in DebugInfo.UnknownRules)
   { %>
            "<%:rule%>",
<% } %>
        ],
        "rules": [
<% foreach (var rule in DebugInfo.DeclaredRules)
   { %>
            { "name": "<%:rule.Key%>", "result": <%:rule.Value.ToString().ToLowerInvariant()%>, },
<% } %>
        ],
        "liquidErrors": [
         <% foreach (var content in DebugInfo.LiquidErrors)
            { %>
            "<a target=_blank style=\"font-weight:bold\" href=\"https://cmstools-si.cm-cic.fr/WEBT_ACEADM/default.aspx?ref=<%: content.ContentUri %>\"><%: content.ContentUri %> / <%:content.RenderUri%></a>",
                <% foreach(var error in content.Errors)
                   { %>
                    "<span title=\"<%: error.Data["tooltip"]%>\"><%: error.Data["elementName"] %> <%:error.Message%></span>",
                <% }
            } %>
        ],
        "liquidErrorsCount": "<%:DebugInfo.LiquidErrorsCount%>",
        "admin": "<%:DebugInfo.IsSprintbarAdmin %>",
    });
</script>

<div class="amc-toolbarreset" data-amc-toolbar-unique="toolbar">

    <div class="amc-toolbar-block">
        <div class="amc-toolbar-icon">
            <span>
                <svg width="24" height="28" xmlns="http://www.w3.org/2000/svg" fill="#3F3F3F" viewBox="0 0 24 24">
                    <path d="M0 0h24v24H0z" fill="none" />
                    <path d="M2 20h20v-4H2v4zm2-3h2v2H4v-2zM2 4v4h20V4H2zm4 3H4V5h2v2zm-4 7h20v-4H2v4zm2-3h2v2H4v-2z" />
                </svg>
                <span class="amc-toolbar-status" data-amc-toolbar-drop-status="contents"></span>
            </span>
        </div>
        <div class="amc-toolbar-info" data-amc-toolbar-drop-info="contents">
        </div>
    </div>

    <div class="amc-toolbar-block">
        <div class="amc-toolbar-icon">
            <span>
                <svg width="24" height="28" xmlns="http://www.w3.org/2000/svg" fill="#3F3F3F" viewBox="0 0 24 24">
                    <path d="M0 0h24v24H0z" fill="none" />
                    <path d="M2 20h20v-4H2v4zm2-3h2v2H4v-2zM2 4v4h20V4H2zm4 3H4V5h2v2zm-4 7h20v-4H2v4zm2-3h2v2H4v-2z" />
                </svg>
                <span class="amc-toolbar-status" data-amc-toolbar-drop-status="tags"></span>
            </span>
        </div>
        <div class="amc-toolbar-info" data-amc-toolbar-drop-info="tags">
        </div>
    </div>

    <div class="amc-toolbar-block">
        <div class="amc-toolbar-icon">
            <span>
                <svg width="16" height="28" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 20">
                    <path fill="#3F3F3F" d="M6 0h4v2h-4zM8 3a8 8 0 1 0 0 16a 8 8 0 1 0 0 -16M7 6h2v4h3v2h-5z" />
                </svg>
                <span class="amc-toolbar-status" data-amc-toolbar-drop-status="timing"></span>
            </span>
        </div>
        <div class="amc-toolbar-info">
            <div class="amc-toolbar-info-piece">
                <b>Total time</b>
                <span class="amc-toolbar-status" data-amc-toolbar-drop-info="timing"></span>
            </div>
        </div>
    </div>

    <div class="amc-toolbar-block" id="changeDateDiv">
        <div class="amc-toolbar-icon">
            <span>
                <svg width="24" height="28" xmlns="http://www.w3.org/2000/svg" fill="#3F3F3F" viewBox="0 0 24 24">
                    <path d="M0 0h24v24H0z" fill="none" />
                    <path d="M19 3h-1V1h-2v2H8V1H6v2H5c-1.11 0-1.99.9-1.99 2L3 19c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm0 16H5V8h14v11zM7 10h5v5H7z" />
                </svg>
                <span class="amc-toolbar-status" data-amc-toolbar-drop-status="changeDate"></span>
            </span>
        </div>
        <div class="amc-toolbar-info">
            <div class="amc-toolbar-info-piece">
                <b>Content display date</b>
                <select data-amc-toolbar-drop-info="changeDate">
                </select>
            </div>
        </div>
    </div>

    <div class="amc-toolbar-block">
        <div class="amc-toolbar-icon">
            <span>
                <svg width="24" height="28" xmlns="http://www.w3.org/2000/svg" fill="#3F3F3F" viewBox="0 0 24 24">
                    <path d="M0 0h24v24H0z" fill="none" />
                    <path d="M3 10h4v10H3zM9 10h12v10H9zM3 4v4h18V4z" />
                </svg>
                <span class="amc-toolbar-status" data-amc-toolbar-drop-status="rules"></span>
            </span>
        </div>
        <div class="amc-toolbar-info" data-amc-toolbar-drop-info="rules">
        </div>
    </div>

    <div class="amc-toolbar-block" id="unknownRulesDiv">
        <div class="amc-toolbar-icon">
            <span>
                <svg width="24" height="28" xmlns="http://www.w3.org/2000/svg" fill="#3F3F3F" viewBox="0 0 24 24">
                    <path d="M0 0h24v24H0z" fill="none" />
                    <path d="M1 21h22L12 2 1 21zm12-3h-2v-2h2v2zm0-4h-2v-4h2v4z" />
                </svg>
                <span class="amc-toolbar-status" data-amc-toolbar-drop-status="unknownRules"></span>
            </span>
        </div>
        <div class="amc-toolbar-info" data-amc-toolbar-drop-info="unknownRules">
        </div>
    </div>
    
    <div class="amc-toolbar-block" id="liquidErrorsDiv">
        <div class="amc-toolbar-icon">
            <span>
                <svg width="24" height="28" xmlns="http://www.w3.org/2000/svg" fill="#3F3F3F" viewBox="0 0 24 24">
                    <path d="M0 0h24v24H0z" fill="none" />
                    <path d="M1 21h22L12 2 1 21zm12-3h-2v-2h2v2zm0-4h-2v-4h2v4z" />
                </svg>
                <span class="amc-toolbar-status" data-amc-toolbar-drop-status="liquidErrors"></span>
            </span>
        </div>
        <div class="amc-toolbar-info" data-amc-toolbar-drop-info="liquidErrors">
        </div>
    </div>
    
    <div class="amc-toolbar-block" id="adminDiv">
        <div class="amc-toolbar-icon">
            <span>
                <svg width="24" height="28" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                    <path fill="#3F3F3F" d="M12 14v2a6 6 0 0 0-6 6H4a8 8 0 0 1 8-8zm0-1c-3.315 0-6-2.685-6-6s2.685-6 6-6s6 2.685 6 6s-2.685 6-6 6zm0-2c2.21 0 4-1.79 4-4s-1.79-4-4-4s-4 1.79-4 4s1.79 4 4 4zm9 6h1v5h-8v-5h1v-1a3 3 0 0 1 6 0v1zm-2 0v-1a1 1 0 0 0-2 0v1h2z" />
                </svg>
                <span class="amc-toolbar-status" data-amc-toolbar-drop-status="admin">Admin</span>
            </span>
        </div>
        <div class="amc-toolbar-info">
            <div class="amc-toolbar-info-piece">
                <a href="<%:GeneratePftlLink().ToString() %>" target="_blank" title="Consulter la trace en PFTL">Trace Pftl</a>
            </div>
            <div class="amc-toolbar-info-piece" title="<%:GetServerName() %>">
                <b><%:GetDataSourceName() %></b>
                <br />
                Client SQL : <b> <%:GetSqlClientName() %></b>
                <span class="amc-toolbar-status"><%:GetDataSourceEnv() %></span>
            </div>
        </div>
    </div>

    <div class="amc-toolbar-block" id="disable-debug">
        <div class="amc-toolbar-icon">
            <span>
                <svg width="24" height="28" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" onclick="javascript:document.cookie='amc-disable-debug=1; Secure'; window.location.reload();" style="cursor: pointer;">
                    <circle cx="8" cy="8" r="6" fill="#3F3F3F"></circle>
                    <path d="M6,5L11,10L10,11L5,6z M11,6L6,11L5,10L10,5z" fill="#FFFFFF"></path>
                </svg>
                <button class="amc-toolbar-status" data-amc-toolbar-drop-status="disable-debug" onclick="javascript:document.cookie='amc-disable-debug=1; Secure'; window.location.reload();">Disable debug</button>
            </span>
        </div>
    </div>

</div>
<!--<![endif]-->
