package BBTools::BBToolsClient;

use JSON::RPC::Client;
use POSIX;
use strict;
use Data::Dumper;
use URI;
use Bio::KBase::Exceptions;
my $get_time = sub { time, 0 };
eval {
    require Time::HiRes;
    $get_time = sub { Time::HiRes::gettimeofday() };
};

use Bio::KBase::AuthToken;

# Client version should match Impl version
# This is a Semantic Version number,
# http://semver.org
our $VERSION = "0.1.0";

=head1 NAME

BBTools::BBToolsClient

=head1 DESCRIPTION





=cut

sub new
{
    my($class, $url, @args) = @_;
    

    my $self = {
	client => BBTools::BBToolsClient::RpcClient->new,
	url => $url,
	headers => [],
    };

    chomp($self->{hostname} = `hostname`);
    $self->{hostname} ||= 'unknown-host';

    #
    # Set up for propagating KBRPC_TAG and KBRPC_METADATA environment variables through
    # to invoked services. If these values are not set, we create a new tag
    # and a metadata field with basic information about the invoking script.
    #
    if ($ENV{KBRPC_TAG})
    {
	$self->{kbrpc_tag} = $ENV{KBRPC_TAG};
    }
    else
    {
	my ($t, $us) = &$get_time();
	$us = sprintf("%06d", $us);
	my $ts = strftime("%Y-%m-%dT%H:%M:%S.${us}Z", gmtime $t);
	$self->{kbrpc_tag} = "C:$0:$self->{hostname}:$$:$ts";
    }
    push(@{$self->{headers}}, 'Kbrpc-Tag', $self->{kbrpc_tag});

    if ($ENV{KBRPC_METADATA})
    {
	$self->{kbrpc_metadata} = $ENV{KBRPC_METADATA};
	push(@{$self->{headers}}, 'Kbrpc-Metadata', $self->{kbrpc_metadata});
    }

    if ($ENV{KBRPC_ERROR_DEST})
    {
	$self->{kbrpc_error_dest} = $ENV{KBRPC_ERROR_DEST};
	push(@{$self->{headers}}, 'Kbrpc-Errordest', $self->{kbrpc_error_dest});
    }

    #
    # This module requires authentication.
    #
    # We create an auth token, passing through the arguments that we were (hopefully) given.

    {
	my %arg_hash2 = @args;
	if (exists $arg_hash2{"token"}) {
	    $self->{token} = $arg_hash2{"token"};
	} elsif (exists $arg_hash2{"user_id"}) {
	    my $token = Bio::KBase::AuthToken->new(@args);
	    if (!$token->error_message) {
	        $self->{token} = $token->token;
	    }
	}
	
	if (exists $self->{token})
	{
	    $self->{client}->{token} = $self->{token};
	}
    }

    my $ua = $self->{client}->ua;	 
    my $timeout = $ENV{CDMI_TIMEOUT} || (30 * 60);	 
    $ua->timeout($timeout);
    bless $self, $class;
    #    $self->_validate_version();
    return $self;
}




=head2 run_RQCFilter_app

  $output = $obj->run_RQCFilter_app($io_params, $run_params)

=over 4

=item Parameter and return types

=begin html

<pre>
$io_params is a BBTools.RQCFilterAppParams
$run_params is a BBTools.RQCFilterParams
$output is a BBTools.RQCFilterAppOutput
RQCFilterAppParams is a reference to a hash where the following keys are defined:
	read_library_ref has a value which is a string
	output_workspace_name has a value which is a string
	output_library_name has a value which is a string
RQCFilterParams is a reference to a hash where the following keys are defined:
	library has a value which is a string
	trimfragadapter has a value which is a BBTools.boolean
	qtrim has a value which is a string
	trimq has a value which is an int
	maxns has a value which is an int
	minavgquality has a value which is an int
	minlength has a value which is an int
	mlf has a value which is a float
	removemouse has a value which is a BBTools.boolean
	removecat has a value which is a BBTools.boolean
	removedog has a value which is a BBTools.boolean
	removehuman has a value which is a BBTools.boolean
	removemicrobes has a value which is a BBTools.boolean
	taxlist has a value which is a reference to a list where each element is a string
	rna has a value which is a BBTools.boolean
	phix has a value which is a BBTools.boolean
	clumpify has a value which is a BBTools.boolean
	dedupe has a value which is a BBTools.boolean
	opticaldupes has a value which is a BBTools.boolean
	khist has a value which is a BBTools.boolean
boolean is an int
RQCFilterAppOutput is a reference to a hash where the following keys are defined:
	report_name has a value which is a string
	report_ref has a value which is a string
	run_command has a value which is a string

</pre>

=end html

=begin text

$io_params is a BBTools.RQCFilterAppParams
$run_params is a BBTools.RQCFilterParams
$output is a BBTools.RQCFilterAppOutput
RQCFilterAppParams is a reference to a hash where the following keys are defined:
	read_library_ref has a value which is a string
	output_workspace_name has a value which is a string
	output_library_name has a value which is a string
RQCFilterParams is a reference to a hash where the following keys are defined:
	library has a value which is a string
	trimfragadapter has a value which is a BBTools.boolean
	qtrim has a value which is a string
	trimq has a value which is an int
	maxns has a value which is an int
	minavgquality has a value which is an int
	minlength has a value which is an int
	mlf has a value which is a float
	removemouse has a value which is a BBTools.boolean
	removecat has a value which is a BBTools.boolean
	removedog has a value which is a BBTools.boolean
	removehuman has a value which is a BBTools.boolean
	removemicrobes has a value which is a BBTools.boolean
	taxlist has a value which is a reference to a list where each element is a string
	rna has a value which is a BBTools.boolean
	phix has a value which is a BBTools.boolean
	clumpify has a value which is a BBTools.boolean
	dedupe has a value which is a BBTools.boolean
	opticaldupes has a value which is a BBTools.boolean
	khist has a value which is a BBTools.boolean
boolean is an int
RQCFilterAppOutput is a reference to a hash where the following keys are defined:
	report_name has a value which is a string
	report_ref has a value which is a string
	run_command has a value which is a string


=end text

=item Description



=back

=cut

 sub run_RQCFilter_app
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 2)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function run_RQCFilter_app (received $n, expecting 2)");
    }
    {
	my($io_params, $run_params) = @args;

	my @_bad_arguments;
        (ref($io_params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"io_params\" (value was \"$io_params\")");
        (ref($run_params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 2 \"run_params\" (value was \"$run_params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to run_RQCFilter_app:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'run_RQCFilter_app');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "BBTools.run_RQCFilter_app",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'run_RQCFilter_app',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method run_RQCFilter_app",
					    status_line => $self->{client}->status_line,
					    method_name => 'run_RQCFilter_app',
				       );
    }
}
 


=head2 run_RQCFilter_local

  $output = $obj->run_RQCFilter_local($io_params, $run_params)

=over 4

=item Parameter and return types

=begin html

<pre>
$io_params is a BBTools.RQCFilterLocalParams
$run_params is a BBTools.RQCFilterParams
$output is a BBTools.RQCFilterLocalOutput
RQCFilterLocalParams is a reference to a hash where the following keys are defined:
	read_library_ref has a value which is a string
	reads_file has a value which is a string
RQCFilterParams is a reference to a hash where the following keys are defined:
	library has a value which is a string
	trimfragadapter has a value which is a BBTools.boolean
	qtrim has a value which is a string
	trimq has a value which is an int
	maxns has a value which is an int
	minavgquality has a value which is an int
	minlength has a value which is an int
	mlf has a value which is a float
	removemouse has a value which is a BBTools.boolean
	removecat has a value which is a BBTools.boolean
	removedog has a value which is a BBTools.boolean
	removehuman has a value which is a BBTools.boolean
	removemicrobes has a value which is a BBTools.boolean
	taxlist has a value which is a reference to a list where each element is a string
	rna has a value which is a BBTools.boolean
	phix has a value which is a BBTools.boolean
	clumpify has a value which is a BBTools.boolean
	dedupe has a value which is a BBTools.boolean
	opticaldupes has a value which is a BBTools.boolean
	khist has a value which is a BBTools.boolean
boolean is an int
RQCFilterLocalOutput is a reference to a hash where the following keys are defined:
	output_directory has a value which is a string
	run_log has a value which is a string
	filtered_fastq_file has a value which is a string
	run_command has a value which is a string

</pre>

=end html

=begin text

$io_params is a BBTools.RQCFilterLocalParams
$run_params is a BBTools.RQCFilterParams
$output is a BBTools.RQCFilterLocalOutput
RQCFilterLocalParams is a reference to a hash where the following keys are defined:
	read_library_ref has a value which is a string
	reads_file has a value which is a string
RQCFilterParams is a reference to a hash where the following keys are defined:
	library has a value which is a string
	trimfragadapter has a value which is a BBTools.boolean
	qtrim has a value which is a string
	trimq has a value which is an int
	maxns has a value which is an int
	minavgquality has a value which is an int
	minlength has a value which is an int
	mlf has a value which is a float
	removemouse has a value which is a BBTools.boolean
	removecat has a value which is a BBTools.boolean
	removedog has a value which is a BBTools.boolean
	removehuman has a value which is a BBTools.boolean
	removemicrobes has a value which is a BBTools.boolean
	taxlist has a value which is a reference to a list where each element is a string
	rna has a value which is a BBTools.boolean
	phix has a value which is a BBTools.boolean
	clumpify has a value which is a BBTools.boolean
	dedupe has a value which is a BBTools.boolean
	opticaldupes has a value which is a BBTools.boolean
	khist has a value which is a BBTools.boolean
boolean is an int
RQCFilterLocalOutput is a reference to a hash where the following keys are defined:
	output_directory has a value which is a string
	run_log has a value which is a string
	filtered_fastq_file has a value which is a string
	run_command has a value which is a string


=end text

=item Description



=back

=cut

 sub run_RQCFilter_local
{
    my($self, @args) = @_;

# Authentication: required

    if ((my $n = @args) != 2)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function run_RQCFilter_local (received $n, expecting 2)");
    }
    {
	my($io_params, $run_params) = @args;

	my @_bad_arguments;
        (ref($io_params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 1 \"io_params\" (value was \"$io_params\")");
        (ref($run_params) eq 'HASH') or push(@_bad_arguments, "Invalid type for argument 2 \"run_params\" (value was \"$run_params\")");
        if (@_bad_arguments) {
	    my $msg = "Invalid arguments passed to run_RQCFilter_local:\n" . join("", map { "\t$_\n" } @_bad_arguments);
	    Bio::KBase::Exceptions::ArgumentValidationError->throw(error => $msg,
								   method_name => 'run_RQCFilter_local');
	}
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "BBTools.run_RQCFilter_local",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'run_RQCFilter_local',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method run_RQCFilter_local",
					    status_line => $self->{client}->status_line,
					    method_name => 'run_RQCFilter_local',
				       );
    }
}
 


=head2 bbtools_version

  $version = $obj->bbtools_version()

=over 4

=item Parameter and return types

=begin html

<pre>
$version is a string

</pre>

=end html

=begin text

$version is a string


=end text

=item Description

Returns the semantic version of the currently installed BBTools. So something like "38.08"

=back

=cut

 sub bbtools_version
{
    my($self, @args) = @_;

# Authentication: none

    if ((my $n = @args) != 0)
    {
	Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
							       "Invalid argument count for function bbtools_version (received $n, expecting 0)");
    }

    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
	    method => "BBTools.bbtools_version",
	    params => \@args,
    });
    if ($result) {
	if ($result->is_error) {
	    Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
					       code => $result->content->{error}->{code},
					       method_name => 'bbtools_version',
					       data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
					      );
	} else {
	    return wantarray ? @{$result->result} : $result->result->[0];
	}
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method bbtools_version",
					    status_line => $self->{client}->status_line,
					    method_name => 'bbtools_version',
				       );
    }
}
 
  
sub status
{
    my($self, @args) = @_;
    if ((my $n = @args) != 0) {
        Bio::KBase::Exceptions::ArgumentValidationError->throw(error =>
                                   "Invalid argument count for function status (received $n, expecting 0)");
    }
    my $url = $self->{url};
    my $result = $self->{client}->call($url, $self->{headers}, {
        method => "BBTools.status",
        params => \@args,
    });
    if ($result) {
        if ($result->is_error) {
            Bio::KBase::Exceptions::JSONRPC->throw(error => $result->error_message,
                           code => $result->content->{error}->{code},
                           method_name => 'status',
                           data => $result->content->{error}->{error} # JSON::RPC::ReturnObject only supports JSONRPC 1.1 or 1.O
                          );
        } else {
            return wantarray ? @{$result->result} : $result->result->[0];
        }
    } else {
        Bio::KBase::Exceptions::HTTP->throw(error => "Error invoking method status",
                        status_line => $self->{client}->status_line,
                        method_name => 'status',
                       );
    }
}
   

sub version {
    my ($self) = @_;
    my $result = $self->{client}->call($self->{url}, $self->{headers}, {
        method => "BBTools.version",
        params => [],
    });
    if ($result) {
        if ($result->is_error) {
            Bio::KBase::Exceptions::JSONRPC->throw(
                error => $result->error_message,
                code => $result->content->{code},
                method_name => 'bbtools_version',
            );
        } else {
            return wantarray ? @{$result->result} : $result->result->[0];
        }
    } else {
        Bio::KBase::Exceptions::HTTP->throw(
            error => "Error invoking method bbtools_version",
            status_line => $self->{client}->status_line,
            method_name => 'bbtools_version',
        );
    }
}

sub _validate_version {
    my ($self) = @_;
    my $svr_version = $self->version();
    my $client_version = $VERSION;
    my ($cMajor, $cMinor) = split(/\./, $client_version);
    my ($sMajor, $sMinor) = split(/\./, $svr_version);
    if ($sMajor != $cMajor) {
        Bio::KBase::Exceptions::ClientServerIncompatible->throw(
            error => "Major version numbers differ.",
            server_version => $svr_version,
            client_version => $client_version
        );
    }
    if ($sMinor < $cMinor) {
        Bio::KBase::Exceptions::ClientServerIncompatible->throw(
            error => "Client minor version greater than Server minor version.",
            server_version => $svr_version,
            client_version => $client_version
        );
    }
    if ($sMinor > $cMinor) {
        warn "New client version available for BBTools::BBToolsClient\n";
    }
    if ($sMajor == 0) {
        warn "BBTools::BBToolsClient version is $svr_version. API subject to change.\n";
    }
}

=head1 TYPES



=head2 boolean

=over 4



=item Description

A boolean - 0 for false, 1 for true.
@range (0, 1)


=item Definition

=begin html

<pre>
an int
</pre>

=end html

=begin text

an int

=end text

=back



=head2 RQCFilterParams

=over 4



=item Description

Contains all parameters for the RQCFilter program, EXCEPT for the inputs and outputs.
Those are added specifically by each function. This lets us describe them separately for the
local function that works mainly against the file system and the app that mainly works against
the Workspace.

This doesn't cover all of the 110+ parameters provided by rqcfilter. Those not listed here
are left as default values, except sketch=f (as that sends data to JGI servers for processing),
barcodefilter=f, and mapk=13.

Notes below are taken from the help output from rqcfilter2.sh ver 38.00

Parameters (format = param name - default - description):
---------------------------------------------------------
library - frag - should be one of 'frag', 'clip', 'lfpe', or 'clrs'.

Adapter trimming parameters:
----------------------------
trimfragadapter - f - Trim all known Illumina adapter sequences, including TruSeq and Nextera.

Quality trimming parameters:
----------------------------
qtrim - f - Trim read ends to remove bases with quality below minq. Performed AFTER looking for kmers.
        Values: rl (trim both ends), f (neither end), r (right end only), l (left end only).
trimq - 10 - Trim quality threshold.  Must also set qtrim for direction, will be ignored if qtrim=f
maxns - 0 - Reads with more Ns than this will be discarded.
minavgquality - 5 - (maq) Reads with average quality (before trimming) below this will be discarded.
minlength - 45 - (ml) Reads shorter than this after trimming will be discarded.  Pairs will be discarded only if both are shorter.
mlf - 0.333 - (minlengthfraction) Reads shorter than this fraction of original length after trimming will be discarded.


Mapping parameters (for vertebrate contaminants):
-------------------------------------------------
removemouse - f - (mouse) Remove mouse reads via mapping.
removecat - f - (cat) Remove cat reads via mapping.
removedog - f - (dog) Remove dog reads via mapping.
removehuman - f - (human) Remove human reads via mapping.

Microbial contaminant removal parameters:
-----------------------------------------
removemicrobes - f - (microbes) Remove common contaminant microbial reads via mapping, and place them in a separate file.
taxlist - emptylist - (tax) Remove these taxa from the database before filtering.  Typically, this would be the organism name or NCBI ID, or a comma-delimited list.  Organism names should have underscores instead of spaces, such as Escherichia_coli.

Filtering parameters (for artificial and microbial contaminants):
-----------------------------------------------------------------
rna - f - Remove reads containing RNA-specific artifacts.
phix - t - Remove reads containing phiX kmers.

Clumpify parameters:
--------------------
clumpify - f - Run clumpify.
dedupe - f - Remove duplicate reads.
opticaldupes - f - Remove optical duplicates (Clumpify optical flag).

Other processing parameters:
----------------------------
khist - f - Set to true to generate a kmer-frequency histogram of the output data. (included in report in the app, as a file in local function)


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
library has a value which is a string
trimfragadapter has a value which is a BBTools.boolean
qtrim has a value which is a string
trimq has a value which is an int
maxns has a value which is an int
minavgquality has a value which is an int
minlength has a value which is an int
mlf has a value which is a float
removemouse has a value which is a BBTools.boolean
removecat has a value which is a BBTools.boolean
removedog has a value which is a BBTools.boolean
removehuman has a value which is a BBTools.boolean
removemicrobes has a value which is a BBTools.boolean
taxlist has a value which is a reference to a list where each element is a string
rna has a value which is a BBTools.boolean
phix has a value which is a BBTools.boolean
clumpify has a value which is a BBTools.boolean
dedupe has a value which is a BBTools.boolean
opticaldupes has a value which is a BBTools.boolean
khist has a value which is a BBTools.boolean

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
library has a value which is a string
trimfragadapter has a value which is a BBTools.boolean
qtrim has a value which is a string
trimq has a value which is an int
maxns has a value which is an int
minavgquality has a value which is an int
minlength has a value which is an int
mlf has a value which is a float
removemouse has a value which is a BBTools.boolean
removecat has a value which is a BBTools.boolean
removedog has a value which is a BBTools.boolean
removehuman has a value which is a BBTools.boolean
removemicrobes has a value which is a BBTools.boolean
taxlist has a value which is a reference to a list where each element is a string
rna has a value which is a BBTools.boolean
phix has a value which is a BBTools.boolean
clumpify has a value which is a BBTools.boolean
dedupe has a value which is a BBTools.boolean
opticaldupes has a value which is a BBTools.boolean
khist has a value which is a BBTools.boolean


=end text

=back



=head2 RQCFilterAppParams

=over 4



=item Description

Parameters for the Narrative App version of RQCFilter.
read_library_ref - UPA for the read library to filter.
output_workspace_name - name of the workspace to put the output reads library and report.
output_library_name - name of the Reads library object produced by the app.


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
read_library_ref has a value which is a string
output_workspace_name has a value which is a string
output_library_name has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
read_library_ref has a value which is a string
output_workspace_name has a value which is a string
output_library_name has a value which is a string


=end text

=back



=head2 RQCFilterAppOutput

=over 4



=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
report_name has a value which is a string
report_ref has a value which is a string
run_command has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
report_name has a value which is a string
report_ref has a value which is a string
run_command has a value which is a string


=end text

=back



=head2 RQCFilterLocalParams

=over 4



=item Description

Parameters for local version of RQCFilter.
read_library_ref - UPA for the read library to filter.
-OR-
reads_file - path to the reads file to filter. Expects an interleaved file, if it's paired end.
If both of the above are given, the read_library_ref takes precedence.


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
read_library_ref has a value which is a string
reads_file has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
read_library_ref has a value which is a string
reads_file has a value which is a string


=end text

=back



=head2 RQCFilterLocalOutput

=over 4



=item Description

The output from the local function version of RQCFilter.

output_directory:
    the path to the output directory containing all files generated by RQCFilter.
run_log:
    the path to the run log from RQCFilter (i.e. its stderr). This will be a path in the
    output directory, added separately here for convenience.
filtered_fastq_file:
    the path to the file (in the output directory) containing the filtered FASTQ reads.
    This will likely be compressed, if you need it decompressed, you can use
    DataFileUtil.unpack_file (see that module).
run_command:
    the string that's run on the command line with all parameters formatted, etc.


=item Definition

=begin html

<pre>
a reference to a hash where the following keys are defined:
output_directory has a value which is a string
run_log has a value which is a string
filtered_fastq_file has a value which is a string
run_command has a value which is a string

</pre>

=end html

=begin text

a reference to a hash where the following keys are defined:
output_directory has a value which is a string
run_log has a value which is a string
filtered_fastq_file has a value which is a string
run_command has a value which is a string


=end text

=back



=cut

package BBTools::BBToolsClient::RpcClient;
use base 'JSON::RPC::Client';
use POSIX;
use strict;

#
# Override JSON::RPC::Client::call because it doesn't handle error returns properly.
#

sub call {
    my ($self, $uri, $headers, $obj) = @_;
    my $result;


    {
	if ($uri =~ /\?/) {
	    $result = $self->_get($uri);
	}
	else {
	    Carp::croak "not hashref." unless (ref $obj eq 'HASH');
	    $result = $self->_post($uri, $headers, $obj);
	}

    }

    my $service = $obj->{method} =~ /^system\./ if ( $obj );

    $self->status_line($result->status_line);

    if ($result->is_success) {

        return unless($result->content); # notification?

        if ($service) {
            return JSON::RPC::ServiceObject->new($result, $self->json);
        }

        return JSON::RPC::ReturnObject->new($result, $self->json);
    }
    elsif ($result->content_type eq 'application/json')
    {
        return JSON::RPC::ReturnObject->new($result, $self->json);
    }
    else {
        return;
    }
}


sub _post {
    my ($self, $uri, $headers, $obj) = @_;
    my $json = $self->json;

    $obj->{version} ||= $self->{version} || '1.1';

    if ($obj->{version} eq '1.0') {
        delete $obj->{version};
        if (exists $obj->{id}) {
            $self->id($obj->{id}) if ($obj->{id}); # if undef, it is notification.
        }
        else {
            $obj->{id} = $self->id || ($self->id('JSON::RPC::Client'));
        }
    }
    else {
        # $obj->{id} = $self->id if (defined $self->id);
	# Assign a random number to the id if one hasn't been set
	$obj->{id} = (defined $self->id) ? $self->id : substr(rand(),2);
    }

    my $content = $json->encode($obj);

    $self->ua->post(
        $uri,
        Content_Type   => $self->{content_type},
        Content        => $content,
        Accept         => 'application/json',
	@$headers,
	($self->{token} ? (Authorization => $self->{token}) : ()),
    );
}



1;
