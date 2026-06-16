package edu.ifsp.loja.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import jakarta.servlet.http.Part;

/**
 * Utilitario para salvar e remover arquivos enviados via upload (multipart/form-data).
 * Mantido fora das camadas de persistencia/service para nao acoplar essas camadas
 * a API de Servlets (jakarta.servlet.http.Part).
 */
public final class FileUploadUtil {
	private FileUploadUtil() {}

	/**
	 * Salva o conteudo do Part em uploadDir com um nome de arquivo unico (UUID),
	 * preservando a extensao original. Retorna o nome do arquivo gerado, ou null
	 * caso nenhum arquivo tenha sido enviado.
	 */
	public static String salvar(Part part, String uploadDir) throws IOException {
		if (part == null || part.getSize() == 0) {
			return null;
		}

		Path dir = Paths.get(uploadDir);
		if (!Files.exists(dir)) {
			Files.createDirectories(dir);
		}

		String nomeOriginal = part.getSubmittedFileName();
		String extensao = "";
		int dotIndex = nomeOriginal == null ? -1 : nomeOriginal.lastIndexOf('.');
		if (dotIndex >= 0) {
			extensao = nomeOriginal.substring(dotIndex);
		}

		String novoNome = UUID.randomUUID() + extensao;
		Path destino = dir.resolve(novoNome);

		try (InputStream in = part.getInputStream();
				OutputStream out = Files.newOutputStream(destino)) {
			in.transferTo(out);
		}

		return novoNome;
	}

	/**
	 * Remove um arquivo de uploadDir, se existir. Falhas sao ignoradas
	 * (ex: arquivo ja removido manualmente).
	 */
	public static void excluir(String uploadDir, String nomeArquivo) {
		if (nomeArquivo == null || nomeArquivo.isBlank()) {
			return;
		}
		try {
			Files.deleteIfExists(Paths.get(uploadDir, nomeArquivo));
		} catch (IOException e) {
			// arquivo orfao: nao impede a operacao principal (ex: exclusao do produto)
		}
	}
}
