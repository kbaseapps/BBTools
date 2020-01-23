"""
Tests for the command and options builder module.
"""
import unittest
from BBTools.utils.commandbuilder import (
    build_options
)


class CommandBuilderTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.available_params = {
            "a_string": {"type": "string"},
            "a_filtered_string": {"type": "string", "allowed_values": ["a", "b", "c"]},
            "a_list": {"type": "list"},
            "a_pos_int": {"type": "int"},
            "an_int": {"type": "int", "allow_neg": True},
            "a_pos_float": {"type": "float"},
            "a_float": {"type": "float", "allow_neg": True},
            "a_boolean": {"type": "boolean"}
        }
        cls.available_params_bad = {
            "bad_type": {"type": "not_real"},
            "bad_string": {"type": "string", "allowed_values": "nope"}
        }

    @classmethod
    def tearDownClass(cls):
        pass

    def test_build_options_good(self):
        param_set = [{
            "a_string": "foo",
            "a_filtered_string": "a",
            "a_pos_int": 5,
            "an_int": -35,
            "a_pos_float": 6.01,
            "a_float": -89.98,
            "a_boolean": "t"
        }, {
            "a_string": ""
        }, {
            "a_list": []
        }, {
            "a_pos_int": 0
        }, {
            "a_pos_float": 0
        }]
        for params in param_set:
            options = build_options(params, self.available_params)
            for p in params:
                if params[p]:
                    self.assertIn("{}={}".format(p, params[p]), options)

    # no longer require all params be present in available_params, so skipping
    @unittest.skip("skipping test_build_options_missing()")
    def test_build_options_missing(self):
        with self.assertRaises(ValueError) as e:
            build_options({"not_a_param": "foo"}, self.available_params)
        self.assertIn("Can't parse unknown parameter", str(e.exception))

    def test_build_options_invalid(self):
        with self.assertRaises(ValueError) as e:
            build_options({"bad_type": "foo"}, self.available_params_bad)
        self.assertIn("Unknown parameter type", str(e.exception))
        with self.assertRaises(ValueError) as e:
            build_options({"bad_string": "bar"}, self.available_params_bad)
        self.assertIn("allowed_values must be a list, received", str(e.exception))

    def test_boolean_good(self):
        for v in [1, "t", "1", "T"]:
            opts = build_options({"a_boolean": v}, self.available_params)
            self.assertIn("a_boolean=t", opts)
        for v in [0, "f", "0", "F"]:
            opts = build_options({"a_boolean": v}, self.available_params)
            self.assertIn("a_boolean=f", opts)

    def test_boolean_bad(self):
        for v in [3, -1, "x"]:
            with self.assertRaises(ValueError) as e:
                build_options({"a_boolean": v}, self.available_params)
            self.assertIn("The value of parameter a_boolean must be one of", str(e.exception))

    def test_int_good(self):
        for v in [0, 1, 123, 12345678, "35", "0"]:
            opts = build_options({"a_pos_int": v}, self.available_params)
            self.assertIn("a_pos_int={}".format(v), opts)
        for v in [0, -1, -123, -12345678, 17, "-365"]:
            opts = build_options({"an_int": v}, self.available_params)
            self.assertIn("an_int={}".format(v), opts)

    def test_int_bad(self):
        for v in [-1, -2, -50, -3657482]:
            with self.assertRaises(ValueError) as e:
                build_options({"a_pos_int": v}, self.available_params)
            self.assertIn("must be >= 0", str(e.exception))
        for v in ["x0", "foo", "bar"]:
            with self.assertRaises(ValueError) as e:
                build_options({"a_pos_int": v}, self.available_params)
            self.assertIn("must be numerical", str(e.exception))

    def test_float_good(self):
        for v in [0.0, 1.1, 123.23, 12345678.456, "35.0", "0.0"]:
            opts = build_options({"a_pos_float": v}, self.available_params)
            self.assertIn("a_pos_float={}".format(v), opts)
        for v in [0.0, -1.1, -123.534, -12345678.134, 17.001, "-365.0"]:
            opts = build_options({"a_float": v}, self.available_params)
            self.assertIn("a_float={}".format(v), opts)

    def test_float_bad(self):
        for v in [-1.1, -123.534, -12345678.134, "-365.0"]:
            with self.assertRaises(ValueError) as e:
                build_options({"a_pos_float": v}, self.available_params)
            self.assertIn("must be >= 0", str(e.exception))
        for v in ["x0", "foo", "bar"]:
            with self.assertRaises(ValueError) as e:
                build_options({"a_float": v}, self.available_params)
            self.assertIn("must be numerical", str(e.exception))

    def test_string_good(self):
        for v in ["a", "foo", "antidisestablishmentarianism", "foo bar"]:
            opts = build_options({"a_string": v}, self.available_params)
            cmp_v = v.replace(" ", "_")
            self.assertIn("a_string={}".format(cmp_v), opts)
        for v in ["a", "b", "c"]:
            opts = build_options({"a_filtered_string": v}, self.available_params)
            self.assertIn("a_filtered_string={}".format(v), opts)

    def test_string_bad(self):
        p_name = "a_filtered_string"
        for v in ["d", "e"]:
            with self.assertRaises(ValueError) as e:
                build_options({p_name: v}, self.available_params)
            self.assertIn("The value of parameter {} must be one of {}, not {}".format(
                p_name, self.available_params[p_name]['allowed_values'], v), str(e.exception))
        with self.assertRaises(ValueError) as e:
            build_options({"a_string": " "}, self.available_params)
        self.assertIn("cannot be an empty string or whitespace", str(e.exception))

    def test_list_good(self):
        data = [{
            "i": ["a", "b", "c"],
            "o": "a,b,c"
        }, {
            "i": ["a 1", "b 2", "c 3"],
            "o": "a_1,b_2,c_3"
        }]
        for p in data:
            opts = build_options({"a_list": p["i"]}, self.available_params)
            self.assertIn("a_list={}".format(p["o"]), opts)

    def test_list_bad(self):
        with self.assertRaises(ValueError) as e:
            build_options({"a_list": ["a", " ", "c"]}, self.available_params)
        self.assertIn("A list with empty or whitespace items is not allowed", str(e.exception))
        with self.assertRaises(ValueError) as e:
            build_options({"a_list": "nope"}, self.available_params)
        self.assertIn("Expected a list as the value for parameter {}, received {}".format("a_list", "nope"), str(e.exception))
