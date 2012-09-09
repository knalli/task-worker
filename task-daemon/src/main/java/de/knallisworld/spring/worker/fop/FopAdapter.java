package de.knallisworld.spring.worker.fop;

import org.apache.commons.io.IOUtils;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Map;

/**
 * This component is the interface to the Apache FOP module.
 * <p/>
 * In a nutshell, this class provides with #render() an easy way to invoke a rendering process.
 */
@Component
public class FopAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(FopAdapter.class);

	/**
	 * the reusable fop factory instance
	 */
	private FopFactory fopFactory;

	/**
	 * the reusable transformer factory
	 */
	private TransformerFactory tFactory;

	@PostConstruct
	public void initialize() {
		fopFactory = FopFactory.newInstance();
		// perhaps it should be checked that this is a compatible transformer factory?
		tFactory = TransformerFactory.newInstance();
	}

	/**
	 * Invoke a rendering of the given xml and xslt file.
	 *
	 * @param xmlFilePath
	 * 		full qualified, absolute path to an xml document
	 * @param xslFilePath
	 * 		full qualified, absolute path to an xsl document
	 * @param mimeType
	 * 		null, optional mime type (if null, MimeConstants.MIME_PDF will be used). This will be used when building a Fop
	 * 		worker instance.
	 * @param outputFoOnly
	 * 		false, if set to true the actual "fop process" will be skipped. The result will be the FO only.
	 * @param userAgentParams
	 * 		an optional map of user agent params. Note: There is only a specific set of valid params available. See
	 * 		#applyUserAgentsParams for more details.
	 * @param transformerParams
	 * 		an optional map of transformer params which will be append directly
	 *
	 * @return
	 *
	 * @throws Exception
	 * 		any internal exception which could be occur
	 */
	public String render(String xmlFilePath, String xslFilePath, String mimeType, boolean outputFoOnly, Map<String, String> userAgentParams, Map<String, String> transformerParams) throws Exception {

		if (!StringUtils.hasText(mimeType)) {
			mimeType = MimeConstants.MIME_PDF;
		}

		File xmlFile;
		File xslFile;
		File outputFile = null;
		BufferedOutputStream outputStream = null;

		try {
			xmlFile = new File(xmlFilePath);
			Assert.state(xmlFile.canRead(), "The xml source could not be found at " + xmlFilePath);
			xslFile = new File(xslFilePath);
			Assert.state(xslFile.canRead(), "The xslt source could not be found at " + xslFilePath);

			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("Xml file: " + xmlFile.getAbsolutePath());
				LOGGER.trace("Xsl file: " + xslFile.getAbsolutePath());
			}

			outputFile = File.createTempFile("random", ".output");
			outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));

			// Setup XSL Transformer
			final Transformer transformer = tFactory.newTransformer(new StreamSource(xslFile));
			applyTransformerParams(transformer, transformerParams);

			Result result;

			// This will be only true if the FO XML was explicit required.
			if (!outputFoOnly) {
				// Setup FO User Agent
				final FOUserAgent userAgent = fopFactory.newFOUserAgent();
				applyUserAgentsParams(userAgent, userAgentParams, xslFile);
				transformer.setParameter("PATH", userAgent.getBaseURL());

				// Setup FOP
				final Fop fop = fopFactory.newFop(mimeType, userAgent, outputStream);

				// Resulting SAX events (the generated FO) must be piped through to FOP
				result = new SAXResult(fop.getDefaultHandler());
			} else {
				result = new StreamResult(outputStream);
			}

			// Start the transformation and rendering process
			transformer.transform(new StreamSource(xmlFile), result);
		} finally {
			outputStream.flush();
			IOUtils.closeQuietly(outputStream);
		}

		return outputFile.getAbsolutePath();
	}

	/**
	 * Apply each parameter as a transformer parameter. They will be available in the xsl itself later.
	 *
	 * @param transformer
	 * @param params
	 */
	private void applyTransformerParams(Transformer transformer, Map<String, String> params) {
		if (transformer == null || params == null || params.isEmpty()) {
			return;
		}

		for (Map.Entry<String, String> entry : params.entrySet()) {
			transformer.setParameter(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * Apply specific user agent parameters.
	 *
	 * @param agent
	 * @param params
	 * @param xslFile
	 */
	private void applyUserAgentsParams(FOUserAgent agent, Map<String, String> params, File xslFile) {
		if (agent == null || params == null || params.isEmpty()) {
			return;
		}

		if (params.containsKey("baseUrl")) {
			String baseUrl = params.get("baseUrl");
			if ("@auto".equals(baseUrl)) {
				agent.setBaseURL("file://" + xslFile.getParentFile().getAbsolutePath());
			} else {
				agent.setBaseURL(baseUrl);
			}
			LOGGER.trace("UserAgent.baseURL = " + agent.getBaseURL());
		}
		if (params.containsKey("author")) {
			agent.setAuthor(params.get("author"));
		}
		if ("@auto".equals(params.get("creationDate"))) {
			agent.setCreationDate(new Date());
		}
		if (params.containsKey("creator")) {
			agent.setCreator(params.get("creator"));
		}
		if (params.containsKey("producer")) {
			agent.setProducer(params.get("producer"));
		}
		if (params.containsKey("title")) {
			agent.setTitle(params.get("title"));
		}
	}

}
