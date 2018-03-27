def build_options(params, available_params):
    """
    params = key-value-pairs. param name -> param value as input (e.g. booleans = 0,1). Any values
        that parse as "Falsy" (empty list, empty string, None type) are omitted from the list of
        options.
    available_params = describe the parameter options. param name -> dict with keys:
        type = one of int, float, boolean, string list, used for parsing
        allow_neg = True or False for numerical parameters,
        strings will have their whitespace replaced with _
    """
    options = []
    for param in params:
        if params[param] != 0 and not params[param]:  # almost falsy, but allow 0
            continue
        elif param not in available_params:
            raise ValueError("Can't parse unknown parameter {}".format(param))
        else:
            param_info = available_params[param]
            t = param_info.get("type", None)
            if t == "int":
                options.append(_process_numerical_parameter(
                    param,
                    params[param],
                    allow_neg=param_info.get("allow_neg", False),
                    is_float=False))
            elif t == "float":
                options.append(_process_numerical_parameter(
                    param,
                    params[param],
                    allow_neg=param_info.get("allow_neg", False),
                    is_float=True))
            elif t == "boolean":
                options.append(_process_boolean_parameter(param, params[param]))
            elif t == "string":
                options.append(_process_string_parameter(
                    param,
                    params[param],
                    allowed_values=param_info.get("allowed_values", [])))
            elif t == "list":
                options.append(_process_list_parameter(
                    param,
                    params[param]))
            else:
                raise ValueError("Unknown parameter type {} for {}!".format(t, param))
    return options


def _process_string_parameter(param, value, allowed_values=[]):
    value = str(value).strip().replace(' ', '_')
    if not value:
        raise ValueError("The value of parameter {} cannot be an empty string or whitespace")
    if not isinstance(allowed_values, list):
        raise ValueError("allowed_values must be a list, received {}".format(allowed_values))
    if allowed_values and value not in allowed_values:
        raise ValueError("The value of parameter {} must be one of {}, not {}".format(param, allowed_values, value))
    return "{}={}".format(param, value)


def _process_list_parameter(param, value):
    if not isinstance(value, list):
        raise ValueError("Expected a list as the value for parameter {}, received {}".format(param, value))
    reformatted = [s.strip().replace(' ', '_') for s in value]
    if '' in reformatted:
        raise ValueError("A list with empty or whitespace items is not allowed")
    return "{}={}".format(param, ",".join(reformatted))


def _process_boolean_parameter(param, value):
    value = str(value).lower()
    result = ""
    if value in ["1", "t"]:
        result = "t"
    elif value in ["0", "f"]:
        result = "f"
    else:
        raise ValueError("The value of parameter {} must be one of [0, 1, t, f], not {}".format(param, value))
    return "{}={}".format(param, result)


def _process_numerical_parameter(param, value, allow_neg=False, is_float=False):
    """ looks for params[param_name], if set, set options[opt_name] appropriately """
    try:
        if is_float:
            value = float(value)
        else:
            value = int(value)
    except:
        raise ValueError("The value of parameter {} was set to {}, but must be numerical!".format(param, value))
    if not allow_neg and value < 0:
        raise ValueError("Parameter {} has value {}, but must be >= 0!".format(param, value))
    return "{}={}".format(param, value)
